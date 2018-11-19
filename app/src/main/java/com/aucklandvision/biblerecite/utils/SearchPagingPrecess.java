package com.aucklandvision.biblerecite.utils;

public class SearchPagingPrecess {
    private int numOfTotalRow;
    private int countNumOfMoreClicked;
    private int beginingNumberToShowAtOnce = 3;
    private int numberToShowAtOnceAfterBegining = 5;
    private int numberToShowAtOnce;                     //beginging -> 3 after -> 10
    private int numberOfOffset;
    private int totalNumClicked;

    public void initSearchPaging(int numOfTotalRow, int countNumOfMoreClicked) {
        setNumOfTotalRow(numOfTotalRow);
        setCountNumOfMoreClicked(countNumOfMoreClicked);
        numberToShowAtOnce = beginingNumberToShowAtOnce;
        totalNumClicked = (numOfTotalRow - beginingNumberToShowAtOnce <= 0 ? 0 : (numOfTotalRow - beginingNumberToShowAtOnce) / numberToShowAtOnceAfterBegining +
                ((numOfTotalRow - beginingNumberToShowAtOnce) % numberToShowAtOnceAfterBegining == 0 ? 0 : 1));
        numberOfOffset = countNumOfMoreClicked * numberToShowAtOnce;
    }

    public void renewNumberOfOffset(int countNumOfMoreClicked) {
        numberOfOffset = countNumOfMoreClicked * numberToShowAtOnce + beginingNumberToShowAtOnce;
    }

    public void changeNumberToShowAtOnce() {
        numberToShowAtOnce = numberToShowAtOnceAfterBegining;
    }

    public void setNumOfTotalRow(int numOfTotalRow) {
        this.numOfTotalRow = numOfTotalRow;
    }

    public void setCountNumOfMoreClicked(int countNumOfMoreClicked) {
        this.countNumOfMoreClicked = countNumOfMoreClicked;
    }

    public int getNumberToShowAtOnce() {
        return numberToShowAtOnce;
    }

    public int getNumberOfOffset() {
        return numberOfOffset;
    }

    public int getTotalNumClicked() {
        return totalNumClicked;
    }
}
