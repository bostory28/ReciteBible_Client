package com.aucklandvision.biblerecite.download;

import android.os.Handler;
import android.os.Message;

import com.aucklandvision.biblerecite.main.MainFragment;
import com.aucklandvision.biblerecite.main.Verses;
import com.aucklandvision.biblerecite.utils.BadgeUtil;
import com.aucklandvision.biblerecite.utils.NetworkInfo;
import com.aucklandvision.biblerecite.utils.ProgressBar;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import vo.UpdateVO;
import vo.VersesVO;

public class DownloadVersesThread extends Thread {
    private DownloadVersesPresenter downloadVersesPresenter;
    private MainFragment mainFragment;
    private MainFragmentHandler mainFragmentHandler;
    private Socket socket;
    private Verses verses;
    private int countNumSuccessed = 0;
    private ProgressBar progressBar;

    public DownloadVersesThread(MainFragment mainFragment, ProgressBar progressBar) {
        downloadVersesPresenter = new DownloadVersesPresenter();
        mainFragmentHandler = new MainFragmentHandler();
        this.mainFragment = mainFragment;
        this.progressBar =progressBar;
    }

    public DownloadVersesThread(MainFragment mainFragment, Verses verses, ProgressBar progressBar) {
        downloadVersesPresenter = new DownloadVersesPresenter();
        mainFragmentHandler = new MainFragmentHandler();
        this.mainFragment = mainFragment;
        this.verses = verses;
        this.progressBar =progressBar;
    }

    @Override
    public void run() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(NetworkInfo.HOST, NetworkInfo.PORT), 7000);

            ArrayList<Object> listOutputClient;
            int countAllVerses = downloadVersesPresenter.getCountVerses();
            if (countAllVerses == 0) {
                /*   step0 case in nothing   */
                //notify nothing to server
                listOutputClient = new ArrayList<>();
                listOutputClient.add(0);  //step 0
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(listOutputClient);
                outputStream.flush();

                //get a data from server
                socket.setSoTimeout(7000);
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                ArrayList<Object> listInput = (ArrayList<Object>) inputStream.readObject();
                ArrayList<VersesVO> listVerses = (ArrayList<VersesVO>) listInput.get(0);
                UpdateVO updateVO = (UpdateVO)listInput.get(1);
                countNumSuccessed = listVerses.size();

                //setting data in android db from server
                if (countNumSuccessed != 0) {
                    downloadVersesPresenter.addVerses((ArrayList<VersesVO>) listInput.get(0));
                    downloadVersesPresenter.updateUserInfoInInit(listVerses.get(0).getYr(), updateVO);
                }
                outputStream.close();
                inputStream.close();
                //setting model and showing items in main thread
                displayMainFragment();
                // if or not push
                BadgeUtil.lastDateClient = updateVO.getLastdate();
            } else {
                /*   step1   */
                //connecting server and send update date to server
                listOutputClient = new ArrayList<>();
                listOutputClient.add(1);
                listOutputClient.add(downloadVersesPresenter.getUpdateDate());

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(listOutputClient);
                outputStream.flush();

                socket.setSoTimeout(7000);
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                ArrayList<Object> listInputClient = (ArrayList<Object>) inputStream.readObject();

                if ((boolean) listInputClient.get(0)) {
                    //same update date between server and client
                    mainFragmentHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.closeProgressbar();
                            mainFragment.showSameDateDialog();
                        }
                    });
                } else {
                    //different update date between server and client
                    /*Arraylist Information
                     * 0: is same version
                     * 1: UpdateVO
                     * 2: VersesVO*/
                    ArrayList<UpdateVO> listUpdate = (ArrayList<UpdateVO>) listInputClient.get(1);
                    countNumSuccessed = listUpdate.size();
                    downloadVersesPresenter.processVersesUpdated(listUpdate);
                    downloadVersesPresenter.updateUserUpdateDate(listUpdate, verses);
                    displayMainFragment();
                    // if or not push
                    BadgeUtil.lastDateClient = listUpdate.get(listUpdate.size()-1).getLastdate();
                }

                outputStream.close();
                inputStream.close();
            }

        } catch (SocketTimeoutException e) {
            if(!socket.isConnected()){
                mainFragmentHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.closeProgressbar();
                        mainFragment.showNetworkFailureDialog();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {

        } finally {
            disconnected();
        }
    }

    public void disconnected() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayMainFragment() {
        mainFragmentHandler.post(new Runnable() {
            @Override
            public void run() {
                mainFragment.getModel();
                mainFragment.showAllItem();
                progressBar.closeProgressbar();
                if (countNumSuccessed != 0)
                    mainFragment.showInfoUpdated();
                mainFragment.showNoDataDialog();
                countNumSuccessed = 0;
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
