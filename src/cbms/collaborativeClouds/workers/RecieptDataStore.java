/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cbms.collaborativeClouds.workers;

/**
 *
 * @author CollaborativeClouds Software Solutions
 */
public class RecieptDataStore {
    public String mId, mName, mBranch;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmBranch() {
        return mBranch;
    }

    public void setmBranch(String mBranch) {
        this.mBranch = mBranch;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
    public int semester;
}
