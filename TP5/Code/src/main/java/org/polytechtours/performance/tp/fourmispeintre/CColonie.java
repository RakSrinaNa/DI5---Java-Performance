package org.polytechtours.performance.tp.fourmispeintre;

/*
 * CColonie.java
 *
 * Created on 11 avril 2007, 16:35
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

import java.util.List;

public class CColonie implements Runnable {

    private boolean mContinue = true;
    private List<CFourmi> mColonie;
    private PaintingAnts mApplis;

    /**
     * Creates a new instance of CColonie
     */
    public CColonie(List<CFourmi> pColonie, PaintingAnts pApplis) {
        mColonie = pColonie;
        mApplis = pApplis;
    }

    public void pleaseStop() {
        mContinue = false;
    }

    @Override
    public void run() {

        while (mContinue) {
            if (!mApplis.getPause()) {
                for (CFourmi f : mColonie) {
                    f.deplacer();
                }
            }
        }
    }

}
