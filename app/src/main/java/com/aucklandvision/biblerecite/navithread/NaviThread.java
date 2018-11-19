package com.aucklandvision.biblerecite.navithread;

import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.aucklandvision.biblerecite.version.VersionInfo;
import com.aucklandvision.biblerecite.R;
import com.aucklandvision.biblerecite.main.MainFragment;
import com.aucklandvision.biblerecite.utils.NetworkInfo;
import com.aucklandvision.biblerecite.utils.ThemeColor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import vo.UpdateVO;

//ver1.1.1
public class NaviThread extends Thread {
    private Socket socket;
    private NaviPresenter naviPresenter;
    private MenuItem menuItem;
    private MainFragmentHandler mainFragmentHandler;
    private MainFragment mainFragment;
    private ArrayList<UpdateVO> listUpdate;
    private int count;
    private String urlToDownloadApk;

    public NaviThread(MainFragment mainFragment, MenuItem menuItem) {
        naviPresenter = new NaviPresenter();
        this.menuItem = menuItem;
        this.mainFragment = mainFragment;
        mainFragmentHandler = new MainFragmentHandler();
    }

    @Override
    public void run() {
        socket = new Socket();
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        try {
            socket.connect(new InetSocketAddress(NetworkInfo.HOST, NetworkInfo.PORT), 7000);
            String lastDateFromClient = naviPresenter.getUpdateDate();

            //step 2 : check if there is a new data.
            ArrayList<Object> listOutputClient = new ArrayList<>();
            listOutputClient.add(2); //step 2 about Navi
            listOutputClient.add(lastDateFromClient);
            listOutputClient.add(VersionInfo.version);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(listOutputClient);
            outputStream.flush();

            socket.setSoTimeout(7000);
            inputStream = new ObjectInputStream(socket.getInputStream());
            ArrayList<Object> listInput = (ArrayList<Object>) inputStream.readObject();
            String lastDataFromServer = (String) listInput.get(0);
            listUpdate = (ArrayList<UpdateVO>) listInput.get(1);
            urlToDownloadApk = (String) listInput.get(2);

            if (lastDataFromServer.equals(lastDateFromClient)) {
                setMenuCounter(0);
            } else {
                setMenuCounter(listUpdate.size());
                mainFragmentHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mainFragment.setDownloadList(listUpdate);
                    }
                });
            }

            if (!urlToDownloadApk.equals("")) {
                mainFragmentHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mainFragment.setInfoNavigation();
                        mainFragment.setNewVersionApp(urlToDownloadApk);
                    }
                });
            }
        } catch (SocketTimeoutException e) {
            Log.d("InitTread-SocketTimeout", e.getMessage());
        } catch (IOException e) {
            Log.d("InitTread-IO", e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.d("InitTread-ClassNotFound", e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setMenuCounter(int countTmp) {
        this.count = countTmp;

        mainFragmentHandler.post(new Runnable() {
            @Override
            public void run() {
                menuItem.setActionView(R.layout.menu_counter);
                TextView view =  (TextView) menuItem.getActionView();
                GradientDrawable bgShape = (GradientDrawable) view.getBackground();
                bgShape.setColor(ThemeColor.ARGB);
                if (count == 0) {
                    bgShape.setAlpha(0);
                }
                view.setText(count > 0 ? String.valueOf(count) : null);
            }
        });
    }

    class MainFragmentHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
