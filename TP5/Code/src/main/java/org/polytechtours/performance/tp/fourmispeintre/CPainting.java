package org.polytechtours.performance.tp.fourmispeintre;
// package PaintingAnts_v2;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;

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
    private BufferedImage img;
    // couleur du fond
    private Color mCouleurFond = new Color(255, 255, 255);
    // dimensions
    private Dimension mDimension;

    private PaintingAnts mApplis;

    private boolean mSuspendu = false;
//    private Graphics mG;

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
        img = new BufferedImage(mDimension.width, mDimension.height, BufferedImage.TYPE_INT_RGB);
        img.getGraphics().setColor(mCouleurFond);
        img.getGraphics().fillRect(0, 0, mDimension.width, mDimension.height);
    }


    public int getCouleurRGB(int x, int y) {
        synchronized (mMutexCouleurs) {
            return img.getRGB(x, y);
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
//            mG = getGraphics();
//            mG.clearRect(0, 0, mDimension.width, mDimension.height);

            // initialisation de la matrice des couleurs

            for (i = 0; i != mDimension.width; i++) {
                for (j = 0; j != mDimension.height; j++) {
                    img.setRGB(i, j, mCouleurFond.getRGB());
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
            suspendre();
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
    }

    @Override
    public void update(Graphics g) {
        paint(img.getGraphics());
        g.drawImage(img, 0, 0, this);
    }

    /******************************************************************************
     * Titre : void colorer_case(int x, int y, Color c) Description : Cette
     * fonction va colorer le pixel correspondant et mettre a jour le tabmleau des
     * couleurs
     ******************************************************************************/
    public void setCouleur(int x, int y, int c, int pTaille) {
        if (!mSuspendu) {
            synchronized (mMutexCouleurs) {
                img.setRGB(x,y,c);
            }

            // on colorie la case sur laquelle se trouve la fourmi
//            getGraphics().setColor(new Color(c));
//            getGraphics().fillRect(x, y, 1, 1);

            // on fait diffuser la couleur :
            switch (pTaille) {
                case 1:
                    convol(mMatriceConv9, mMatriceFactor9, x, y);
                    this.repaint(x - mMatriceConv9.length / 2, y - mMatriceConv9.length / 2, mMatriceConv9.length, mMatriceConv9.length);
                    break;
                case 2:
                    convol(mMatriceConv25, mMatriceFactor25, x, y);
                    this.repaint(x - mMatriceConv25.length / 2, y - mMatriceConv25.length / 2, mMatriceConv25.length, mMatriceConv25.length);
                    break;
                case 3:
                    convol(mMatriceConv49, mMatriceFactor49, x, y);
                    this.repaint(x - mMatriceConv49.length / 2, y - mMatriceConv49.length / 2, mMatriceConv49.length, mMatriceConv49.length);
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
                        int c = img.getRGB(m, n);
                        R += matrix[k][l] * ((c & 0x00FF0000) >>> 16);
                        G += matrix[k][l] * ((c & 0x0000FF00) >>> 8);
                        B += matrix[k][l] * (c & 0x000000FF);
                    }
                }

                int m = (x + i - matrix.length / 2 + mDimension.width) % mDimension.width;
                int n = (y + j - matrix[i].length / 2 + mDimension.height) % mDimension.height;
                img.setRGB(m, n,  0xFF000000 | ((int)(R / factor)) << 16 | ((int)(G / factor)) << 8 | ((int)(B / factor)));
                if (!mSuspendu) {
//                    img.getGraphics().setColor(new Color(getCouleurRGB(m,n)));
//                    img.getGraphics().fillRect(m, n, 1, 1);
                }
            }
        }
//        mG.setColor(Color.RED);
//        mG.fillRect(0, 0, 100, 100);
    }

    public void suspendre() {
        mSuspendu = !mSuspendu;
        if (!mSuspendu) {
            this.repaint();
        }
    }
}
