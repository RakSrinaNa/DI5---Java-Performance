package org.polytechtours.performance.tp.fourmispeintre;
// package PaintingAnts_v2;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// version : 2.0

/**
 * <p>
 * Titre : Painting Ants
 * </p>
 * <p>
 * Description :
 * </p>
 * <p>
 * Copyright : Copyright (c) 2003
 * </p>
 * <p>
 * Société : Equipe Réseaux/TIC - Laboratoire d'Informatique de l'Université de
 * Tours
 * </p>
 *
 * @author Nicolas Monmarché
 * @version 1.0
 */

public class CPainting extends Canvas implements MouseListener {
    private static final long serialVersionUID = 1L;
    // matrice servant pour le produit de convolution
    static private float mMatriceFactor9 = 16f;
    static private int[][] mMatriceConv9 = {
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1},
    };
    static private float mMatriceFactor25 = 44f;
    static private int[][] mMatriceConv25 = {
            {1, 1, 2, 1, 1},
            {1, 2, 3, 2, 1},
            {2, 3, 4, 3, 2},
            {1, 2, 3, 2, 1},
            {1, 1, 2, 1, 1}
    };
    static private float mMatriceFactor49 = 128f;
    static private int[][] mMatriceConv49 = {
            {1, 1, 2, 2, 2, 1, 1},
            {1, 2, 3, 4, 3, 2, 1},
            {2, 3, 4, 5, 4, 3, 2},
            {2, 4, 5, 8, 5, 4, 2},
            {2, 3, 4, 5, 4, 3, 2},
            {1, 2, 3, 4, 3, 2, 1},
            {1, 1, 2, 2, 2, 1, 1}
    };
    // Objet ne servant que pour les bloc synchronized pour la manipulation du
    // tableau des couleurs
    private final Object mMutexCouleurs = new Object();
    // Objet de type Graphics permettant de manipuler l'affichage du Canvas
    // tableau des couleurs, il permert de conserver en memoire l'état de chaque
    // pixel du canvas, ce qui est necessaire au deplacemet des fourmi
    // il sert aussi pour la fonction paint du Canvas
    private int[][][] mCouleurs;
    // couleur du fond
    private Color mCouleurFond = new Color(255, 255, 255);
    // dimensions
    private Dimension mDimension;

    private PaintingAnts mApplis;

    private boolean mSuspendu = false;
    private Graphics mG;

    /******************************************************************************
     * Titre : public CPainting() Description : Constructeur de la classe
     ******************************************************************************/
    public CPainting(Dimension pDimension, PaintingAnts pApplis) {
        int i, j;
        addMouseListener(this);

        mApplis = pApplis;

        mDimension = pDimension;
        setBounds(new Rectangle(0, 0, mDimension.width, mDimension.height));

        this.setBackground(mCouleurFond);

        // initialisation de la matrice des couleurs
        mCouleurs = new int[mDimension.width][mDimension.height][3];
        for (i = 0; i != mDimension.width; i++) {
            for (j = 0; j != mDimension.height; j++) {
                mCouleurs[i][j][0] = mCouleurFond.getRed();
                mCouleurs[i][j][1] = mCouleurFond.getGreen();
                mCouleurs[i][j][2] = mCouleurFond.getBlue();
            }
        }
    }

    /******************************************************************************
     * Titre : Color getCouleur Description : Cette fonction renvoie la couleur
     * d'une case
     ******************************************************************************/
    public int[] getCouleur(int x, int y) {
        synchronized (mMutexCouleurs) {
            return mCouleurs[x][y];
        }
    }

    public int getCouleurRGB(int x, int y) {
        synchronized (mMutexCouleurs) {
            return 0xFF000000 | mCouleurs[x][y][0] << 16 | mCouleurs[x][y][1] << 8 | mCouleurs[x][y][2];
        }
    }

    /******************************************************************************
     * Titre : Color getDimension Description : Cette fonction renvoie la
     * dimension de la peinture
     ******************************************************************************/
    public Dimension getDimension() {
        return mDimension;
    }

    /******************************************************************************
     * Titre : Color getHauteur Description : Cette fonction renvoie la hauteur de
     * la peinture
     ******************************************************************************/
    public int getHauteur() {
        return mDimension.height;
    }

    /******************************************************************************
     * Titre : Color getLargeur Description : Cette fonction renvoie la hauteur de
     * la peinture
     ******************************************************************************/
    public int getLargeur() {
        return mDimension.width;
    }

    /******************************************************************************
     * Titre : void reset() Descri0;ption : Initialise le fond a la couleur blanche
     * et initialise le tableau des couleurs avec la couleur blanche
     ******************************************************************************/
    public void reset() {
        int i, j;
        synchronized (mMutexCouleurs) {
            mG = getGraphics();
            mG.clearRect(0, 0, mDimension.width, mDimension.height);

            // initialisation de la matrice des couleurs

            for (i = 0; i != mDimension.width; i++) {
                for (j = 0; j != mDimension.height; j++) {
                    mCouleurs[i][j][0] = mCouleurFond.getRed();
                    mCouleurs[i][j][1] = mCouleurFond.getGreen();
                    mCouleurs[i][j][2] = mCouleurFond.getBlue();
                }
            }
        }

        mSuspendu = false;
    }

    /****************************************************************************/
    public void mouseClicked(MouseEvent pMouseEvent) {
        pMouseEvent.consume();
        if (pMouseEvent.getButton() == MouseEvent.BUTTON1) {
            mApplis.pause();
        } else if (pMouseEvent.getButton() == MouseEvent.BUTTON2) {
            suspendre();
        } else {
            reset();
        }
    }

    /****************************************************************************/
    public void mouseEntered(MouseEvent pMouseEvent) {
    }

    /****************************************************************************/
    public void mouseExited(MouseEvent pMouseEvent) {
    }

    /****************************************************************************/
    public void mousePressed(MouseEvent pMouseEvent) {

    }

    /****************************************************************************/
    public void mouseReleased(MouseEvent pMouseEvent) {
    }

    /******************************************************************************
     * Titre : void paint(Graphics g) Description : Surcharge de la fonction qui
     * est appelé lorsque le composant doit être redessiné
     ******************************************************************************/
    @Override
    public void paint(Graphics pGraphics) {
        for (int i = 0; i < mDimension.width; i++) {
            for (int j = 0; j < mDimension.height; j++) {
                pGraphics.setColor(new Color(getCouleurRGB(i, j)));
                pGraphics.fillRect(i, j, 1, 1);
            }
        }
    }

    /******************************************************************************
     * Titre : void colorer_case(int x, int y, Color c) Description : Cette
     * fonction va colorer le pixel correspondant et mettre a jour le tabmleau des
     * couleurs
     ******************************************************************************/
    public void setCouleur(int x, int y, int[] c, int pTaille) {
        if (!mSuspendu) {
            synchronized (mMutexCouleurs) {
                mCouleurs[x][y][0] = c[0];
                mCouleurs[x][y][1] = c[1];
                mCouleurs[x][y][2] = c[2];
            }

            // on colorie la case sur laquelle se trouve la fourmi
            mG.setColor(new Color(c[0], c[1], c[2]));
            mG.fillRect(x, y, 1, 1);

            // on fait diffuser la couleur :
            switch (pTaille) {
                case 1:
                    convol(mMatriceConv9, mMatriceFactor9, x, y);
                    break;
                case 2:
                    convol(mMatriceConv25, mMatriceFactor25, x, y);
                    break;
                case 3:
                    convol(mMatriceConv49, mMatriceFactor49, x, y);
                    break;
            }// end switch
        }
    }

    /******************************************************************************
     * Titre : setSupendu Description : Cette fonction change l'état de suspension
     ******************************************************************************/

    public void convol(int[][] matrix, float factor, int x, int y) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int R = 0, G = 0, B = 0;

                for (int k = 0; k < matrix.length; k++) {
                    for (int l = 0; l < matrix[i].length; l++) {
                        int m = (x + i + k - matrix.length + 1 + mDimension.width) % mDimension.width;
                        int n = (y + j + l - matrix[i].length + 1 + mDimension.height) % mDimension.height;
                        R += matrix[k][l] * mCouleurs[m][n][0];
                        G += matrix[k][l] * mCouleurs[m][n][1];
                        B += matrix[k][l] * mCouleurs[m][n][2];
                    }
                }


                int m = (x + i - matrix.length / 2 + mDimension.width) % mDimension.width;
                int n = (y + j - matrix[i].length / 2 + mDimension.height) % mDimension.height;
                mCouleurs[m][n][0] = (int) (R/factor);
                mCouleurs[m][n][1] = (int) (G/factor);
                mCouleurs[m][n][2] = (int) (B/factor);
                if (!mSuspendu) {
                    mG.setColor(new Color(getCouleurRGB(m,n)));
                    mG.fillRect(m, n, 1, 1);
                }
            }
        }
        mG.setColor(Color.RED);
        mG.fillRect(0, 0, 100, 100);
    }

    public void suspendre() {
        mSuspendu = !mSuspendu;
        if (!mSuspendu) {
            repaint();
        }
    }
}
