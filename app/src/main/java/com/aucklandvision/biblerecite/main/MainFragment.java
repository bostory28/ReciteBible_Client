package com.aucklandvision.biblerecite.main;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.aucklandvision.biblerecite.MainActivity;
import com.aucklandvision.biblerecite.R;
import com.aucklandvision.biblerecite.download.DownloadDialog;
import com.aucklandvision.biblerecite.favourite.FavouriteDialog;
import com.aucklandvision.biblerecite.download.DownloadVersesThread;
import com.aucklandvision.biblerecite.font.FontDialog;
import com.aucklandvision.biblerecite.information.InformationView;
import com.aucklandvision.biblerecite.navithread.NaviThread;
import com.aucklandvision.biblerecite.schedule.ScheduleDialog;
import com.aucklandvision.biblerecite.search.SearchDialog;
import com.aucklandvision.biblerecite.theme.ThemeDialog;
import com.aucklandvision.biblerecite.utils.BadgeUtil;
import com.aucklandvision.biblerecite.utils.CalendarEnglishVer;
import com.aucklandvision.biblerecite.utils.DeleteApkFile;
import com.aucklandvision.biblerecite.utils.ProgressBar;
import com.aucklandvision.biblerecite.utils.SetVerseByLevel;
import com.aucklandvision.biblerecite.utils.FontUtil;
import com.aucklandvision.biblerecite.utils.ThemeColor;

import java.util.ArrayList;
import vo.UpdateVO;

public class MainFragment extends Fragment implements MainContract.View {
    private MainFragment mainFragment;
    private MainPresenter mainPresenter;
    private MainActivity mainActivity;
    private UserInfo userInfo;
    private Verses verses;
    private RelativeLayout mainView_container;
    private TextView textView_title;
    private TextView textView_content;
    private SetVerseByLevel setVerseByLevel;
    private CheckBox check_remember;
    private Toolbar toolbar;
    //touch event
    boolean isTrueLongClick = false;
    private ArrayList<Verses> listVerse;
    //favorite
    private CheckBox check_favorite;
    //Navigation
    private Menu menu;
    private ArrayList<UpdateVO> listUpdate;
    private boolean isNewVersion = false;
    private String urlToDownloadApk = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.verse_content, container, false);
        setHasOptionsMenu(true);
        //declare
        this.mainFragment = this;
        int states[][] = {{android.R.attr.state_checked}, {}};
        int colors[] = {ThemeColor.ARGB, ThemeColor.ARGB};

        if(Build.VERSION.SDK_INT < 23) {
            mainActivity = (MainActivity) getActivity();
        }

        mainView_container = (RelativeLayout) rootView.findViewById(R.id.mainView_container);
        textView_title = (TextView) rootView.findViewById(R.id.textView_title);
        textView_title.setTextColor(0xff3C3F41);
        textView_title.setTypeface(FontUtil.typeface);
        textView_content = (TextView) rootView.findViewById(R.id.textView_content);
        textView_content.setTextSize(TypedValue.COMPLEX_UNIT_DIP, FontUtil.fontSize);
        textView_content.setTypeface(FontUtil.typeface);
        check_remember = (CheckBox) rootView.findViewById(R.id.check_remember);
        check_favorite = (CheckBox) rootView.findViewById(R.id.check_favorite);
        CompoundButtonCompat.setButtonTintList(check_remember, new ColorStateList(states, colors));
        toolbar = (Toolbar) mainActivity.getToolbar();
        setVerseByLevel = new SetVerseByLevel();
        //event
        updateRememberCheckbox();
        updateFavoriteCheckbox();
        touchEventOfMainView();
        touchEventVerseTitle();
        //set presenter
        setMainPresenter();
        //get model
        getModel();
        //show item
        showAllItem();
        //case in no data
        showNoDataDialog();
        return rootView;
    }

    public void showAllItem() {
        showToolbarTitle();
        showRememberCheckbox();
        showFavoriteCheckbox();
        showVerse();
    }

    public void getModel() {
        getUserinfo();
        getVerse();
    }

    @Override
    public void showToolbarTitle() {
        switch (userInfo.getLanguage()) {
            case 0:
                toolbar.setTitle(verses.getYr() + "년 " + verses.getMon()+"월");
                break;
            case 1:
                if (verses.getMon() != 0)
                    toolbar.setTitle(CalendarEnglishVer.calMonth(verses.getMon()) + " " + verses.getYr());
                else
                    toolbar.setTitle("");
                break;
        }
    }

    @Override
    public void showRememberCheckbox() {
        if (userInfo.getLanguage() == 0)
            check_remember.setChecked(verses.getCheck_remember_kr() == 0 ? false : true);
        else
            check_remember.setChecked(verses.getCheck_remember_en() == 0 ? false : true);
    }

    public void showFavoriteCheckbox() {
        check_favorite.setChecked(verses.getFavorite() == 0 ? false : true);
    }

    @Override
    public void showVerse() {
        textView_title.setText((userInfo.getLanguage() == 0 ? verses.getVerse_title_kr() : verses.getVerse_title_en()));
        textView_content.setText(setVerseByLevel.setVerseByLevel(verses, userInfo));
    }

    @Override
    public void updateRememberCheckbox() {
        check_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mainPresenter.updateRememberCheckbox(verses.get_id(), isChecked == true ? 1 : 0, userInfo.getLanguage());
                getVerse();
            }
        });
    }

    public void updateFavoriteCheckbox() {
        check_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mainPresenter.updateFavoriteCheckbox(verses.get_id(), isChecked == true ? 1 : 0);
                getVerse();
            }
        });
    }

    public void touchEventVerseTitle() {
        textView_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChk = check_remember.isChecked();
                if (isChk) check_remember.setChecked(false);
                else check_remember.setChecked(true);
            }
        });
    }

    @Override
    public void touchEventOfMainView() {
        mainView_container.setOnTouchListener(touchEvent);
        textView_content.setOnTouchListener(touchEvent);
        mainView_container.setOnLongClickListener(longTouchEvent);
        textView_content.setOnLongClickListener(longTouchEvent);
    }
    private View.OnTouchListener touchEvent = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

                int whichTouch = mainPresenter.decideTouchEventSelected(event);
                switch (whichTouch) {
                    case 1:
                        if (!isTrueLongClick) {
                            ArrayList<Object> listModel = mainPresenter.getModelsMovedIntoPage();
                            switch ((Integer) listModel.get(0)) {
                                case 0:
                                    userInfo = (UserInfo) listModel.get(1);
                                    verses = (Verses) listModel.get(2);
                                    showAllItem();
                                    break;
                                case 1:
                                    if (userInfo.getLanguage() == 0)
                                        Snackbar.make(v, "첫페이지입니다", Snackbar.LENGTH_SHORT).show();
                                    else
                                        Snackbar.make(v, "This is the first Page", Snackbar.LENGTH_SHORT).show();
                                    break;
                                case 2:
                                    if (userInfo.getLanguage() == 0)
                                        Snackbar.make(v, "마지막페이지입니다", Snackbar.LENGTH_SHORT).show();
                                    else
                                        Snackbar.make(v, "This is the last Page", Snackbar.LENGTH_SHORT).show();
                                    break;
                                }
                        } else {
                            showVerse();
                        }
                        isTrueLongClick = false;
                        break;
                    case 2:
                        if (!isTrueLongClick)
                            showQuizLevelDialog();
                        else {
                            showVerse();
                        }
                        isTrueLongClick = false;
                        break;
                    case 3:
                        showVerse();
                        isTrueLongClick = false;
                        break;
            }
            return false;
        }
    };
    private View.OnLongClickListener longTouchEvent = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            isTrueLongClick = true;
            textView_content.setText(setVerseByLevel.showVerseInstantly(verses, userInfo));
            return true;
        }
    };

    @Override
    public void showQuizLevelDialog() {
        ArrayList<String> list = new ArrayList<>();
        if (userInfo.getLanguage() == 0) {
            list.add("1단계 (전체보기)");
            list.add("2단계");
            list.add("3단계");
            list.add("4단계");
        } else {
            list.add("1 STEP (SHOW ALL WORDS)");
            list.add("2 STEP");
            list.add("3 STEP");
            list.add("4 STEP");
        }
        CharSequence[] items = list.toArray(new String[list.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity, R.style.QuizDialogStyle);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mainPresenter.updateQuizLevel(verses.get_id(), which);
                getVerse();
                showVerse();
            }
        });
        builder.show();
    }

    public void displayNavigationItems(int id) {
        if (id == R.id.nav_schedule) {
            new ScheduleDialog(this).showScheduleDialog();
        } else if (id == R.id.nav_search) {
            new SearchDialog(this).showSearchDialog();
        } else if (id == R.id.nav_favorite) {
            new FavouriteDialog(this).showFavoriteDialog();
        } else if (id == R.id.nav_download) {
            new DownloadDialog(this).showDownloadDialog();
        } else if (id == R.id.nav_font) {
            new FontDialog(this).showFontDialog();
        } else if (id == R.id.nav_theme) {
            new ThemeDialog(this).showThemeDialog();
        } else if (id == R.id.nav_info) {
            new InformationView(this).showInfoDialog();
        }
    }

    public void setInfoNavigation() {
        MenuItem menuItem = menu.getItem(4).getSubMenu().getItem(2);
        menuItem.setActionView(R.layout.menu_counter);
        TextView view = (TextView) menuItem.getActionView();
        GradientDrawable bgShape = (GradientDrawable) view.getBackground();
        bgShape.setColor(ThemeColor.ARGB);
        view.setText("U");
        isNewVersion = true;
    }

    public void showNoDataDialog() {
        if (textView_content.getText().length() == 0) {
            ProgressBar progressBar = new ProgressBar(mainActivity);
            progressBar.showProgressbar();
            DownloadVersesThread downloadVersesThread = new DownloadVersesThread(mainFragment, progressBar);
            downloadVersesThread.start();
        }
    }

    public void showNetworkFailureDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        ProgressBar progressBar = new ProgressBar(mainActivity);
                        progressBar.showProgressbar();

                        mainPresenter.updateUserInfo(userInfo);
                        DownloadVersesThread downloadVersesThread = new DownloadVersesThread(mainFragment, verses, progressBar);
                        downloadVersesThread.start();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        if (textView_content.getText().length() == 0) {
                            mainActivity.finish();
                        } else {
                            dialog.cancel();
                        }
                        break;
                }
            }
        };

        AlertDialog.Builder reBuilder = new AlertDialog.Builder(mainActivity);
        reBuilder.setCancelable(false);
        reBuilder.setIcon(R.drawable.ic_title_failure);
        if (userInfo.getLanguage() == 0) {
            reBuilder.setTitle("네트워크 실패");
            reBuilder.setMessage("서버가 종료상태에 있어서 연결하지 못 했습니다. \n서버에 다시 연결하시겠습니까?").setPositiveButton("네", dialogClickListener).setNegativeButton("아니오", dialogClickListener).show();
        } else {
            reBuilder.setTitle("Network Failure");
            reBuilder.setMessage("There is no connection. The server might be shut down. Do you want to connect to the server again?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
        }
    }

    public void showSameDateDialog() {
        // init badge
        BadgeUtil badgeUtil = new BadgeUtil(mainActivity);
        badgeUtil.setInitBadgeNumber();
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        if (userInfo.getLanguage() == 0) {
            builder.setTitle("정보");
            builder.setMessage("최신버전입니다.")
                    .setCancelable(false)
                    .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
        } else {
            builder.setTitle("Information");
            builder.setMessage("It is the latest version.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
        }
        builder.setIcon(R.drawable.ic_title_information);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showInfoUpdated() {
        // init badge
        BadgeUtil badgeUtil = new BadgeUtil(mainActivity);
        badgeUtil.setInitBadgeNumber();

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        if (userInfo.getLanguage() == 0) {
            builder.setTitle("정보");
            builder.setMessage("다운로드를 완료하였습니다.")
                    .setCancelable(false)
                    .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            setUpdateCountNull();
                            listUpdate = null;
                        }
                    });
        } else {
            builder.setTitle("Information");
            builder.setMessage("Download has been completed.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            setUpdateCountNull();
                            listUpdate = null;
                        }
                    });
        }
        builder.setIcon(R.drawable.ic_title_information);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void setUpdateCountNull() {
        menu.getItem(3).setActionView(R.layout.menu_counter);
        TextView view = (TextView) menu.getItem(3).getActionView();
        GradientDrawable bgShape = (GradientDrawable) view.getBackground();
        bgShape.setAlpha(0);
        view.setText(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(0, 0, 0, "한글(개역개정)");
        menu.add(0, 1, 0, "ENGLISH(GNTD)");
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                if (userInfo.getLanguage() == 1) {
                    userInfo.setLanguage(0);
                    showAllItem();
                }
                break;
            case 1:
                if (userInfo.getLanguage() == 0) {
                    userInfo.setLanguage(1);
                    showAllItem();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        getModel();
        showAllItem();
        //ver1.1.1
        if (textView_content.getText().length() != 0) {
            NaviThread naviThread = new NaviThread(mainFragment, menu.getItem(3));
            naviThread.start();
        }
        isNewVersion = false;
        new DeleteApkFile(mainActivity).deleteApkFiles();
    }

    @Override
    public void onPause() {
        super.onPause();
        mainPresenter.updateUserInfo(userInfo);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    @Override
    public void setMainPresenter() {
        mainPresenter = new MainPresenter();
    }

    @Override
    public void getUserinfo() {
        userInfo = mainPresenter.getCurrUserInfo();
    }

    @Override
    public void getVerse() {
        verses = mainPresenter.getCurrVerse();
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public boolean getIsNewVersion() {
        return isNewVersion;
    }

    public String getUrlToDownloadApk() {
        return urlToDownloadApk;
    }

    public void setNewVersionApp(String urlToDownloadApk) {
        this.urlToDownloadApk = urlToDownloadApk;
    }

    public void setDownloadList(ArrayList<UpdateVO> listUpdate) {
        this.listUpdate = listUpdate;
    }

    public ArrayList<UpdateVO> getDownloadList() {
        return listUpdate;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public TextView getTextView_content() {
        return textView_content;
    }

    public TextView getTextView_title() {
        return textView_title;
    }

    public MainPresenter getMainPresenter() {
        return mainPresenter;
    }

    public void setListVerse(ArrayList<Verses> listVerse) {
        this.listVerse = listVerse;
    }

    public Verses getVerses() {
        return verses;
    }

    public void setVerse(Verses verses) {
        this.verses = verses;
    }
}
