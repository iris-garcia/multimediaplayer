/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iris.imagen;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.RescaleOp;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sm.image.LookupTableProducer;

/**
 *
 * @author Iris
 */
public class LienzoImageToolBar extends JPanel {
    private final JPanel rotPanel, zoomPanel, contPanel, filtPanel, umbPanel;
    public JDesktopPane desktop;
    public String filtro;

    private JButton btnTest;

    public LienzoImageToolBar(JDesktopPane desk) {
        this.desktop = desk;
        GridBagConstraints c;      
        
        rotPanel = createRotPanel();
        zoomPanel = createZoomPanel();
        contPanel = createContPanel();
        filtPanel = createFiltPanel();
        umbPanel = createUmbPanel();
        
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(300, 200));
        this.setMaximumSize(new Dimension(300, 400));
        this.setBorder(BorderFactory.createTitledBorder("Imagen:"));

        // Panel rotación
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 1;
        c.weightx = 1;
        this.add(rotPanel, c);

        // Panel zoom
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weighty = 0;
        c.weightx = 0;
        this.add(zoomPanel, c);

        // Panel contraste
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weighty = 1;
        c.weightx = 1;
        this.add(contPanel, c);

        // Panel filtro
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 1;
        c.weightx = 1;
        this.add(filtPanel, c);
        
        // Panel umbralización
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 1;
        c.weightx = 1;
        this.add(umbPanel, c);
    }

    private JPanel createRotPanel() {
        JPanel panel = new JPanel();
        GridBagConstraints c;
        JButton rot90 = new JButton();
        JButton rot180 = new JButton();
        JButton rot270 = new JButton();
        MyButtonGroup rotationBtns = new MyButtonGroup();
        JSlider rotationSli = new JSlider(JSlider.HORIZONTAL, 0, 360, 1);

        ImageIcon icon90 = new ImageIcon(Lienzo.class.getResource("/com/iris/iconos/rotacion90.png"));
        ImageIcon icon180 = new ImageIcon(Lienzo.class.getResource("/com/iris/iconos/rotacion180.png"));
        ImageIcon icon270 = new ImageIcon(Lienzo.class.getResource("/com/iris/iconos/rotacion270.png"));

        // Atributos panel rotación
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Rotación:"));
        panel.setPreferredSize(new Dimension(150,100));
        
        rotationSli.setPreferredSize(new Dimension(100,25));
        
        rot90.setIcon(icon90);
        rot180.setIcon(icon180);
        rot270.setIcon(icon270);
        rot90.setName("rot90");
        rot180.setName("rot180");
        rot270.setName("rot270");
        rotationBtns.add(rot90);
        rotationBtns.add(rot180);
        rotationBtns.add(rot270);
        
        // Botón rot90
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(rot90, c);
        
        // Botón rot180
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(rot180, c);
        
        // Botón rot90
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(rot270, c);
        
        // Slider rotación
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.gridwidth = 3;
        panel.add(rotationSli, c);      
        
        // Listeners
        // Botones de rotación
        for (Enumeration<AbstractButton> buttons = rotationBtns.getElements();
           buttons.hasMoreElements();){
            final AbstractButton button = buttons.nextElement();

            button.addMouseListener(new MouseAdapter(){
               public void mouseClicked(MouseEvent evt){
                  String botonStr = button.getName();
                  double r = 0;

                  switch(botonStr){
                  case "rot90":
                     r = Math.toRadians(90);
                     break;
                  case "rot180":
                     r = Math.toRadians(180);
                     break;
                  case "rot270":
                     r = Math.toRadians(270);
                     break;
                  }

                  Container vi = desktop.getSelectedFrame().getContentPane();

                  if (vi instanceof LienzoImage){
                     LienzoImage li = (LienzoImage) vi;
                     BufferedImage imgSource = li.getFilteredImageRGB();
                     try{
                        Point p = new Point(imgSource.getWidth()/2, imgSource.getHeight()/2);
                        AffineTransform at = AffineTransform.getRotateInstance(r, p.x, p.y);
                        AffineTransformOp atop;
                        atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                        BufferedImage imgdest = atop.filter(imgSource, null);
                        li.setImage(imgdest);
                        li.removeShapes();
                        li.repaint();
                     }catch(IllegalArgumentException e){
                        System.err.println(e.getLocalizedMessage());
                     }
                  }
               }
            });
        }
        
        // Slider rotación
        rotationSli.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent evt){
               int value = ((JSlider) evt.getSource()).getValue();
               Container vi = desktop.getSelectedFrame().getContentPane();

               if (vi instanceof LienzoImage){
                  LienzoImage li = (LienzoImage) vi;
                  BufferedImage imgSource = li.getImage();
                  try{
                     Point p = new Point(imgSource.getWidth()/2, imgSource.getHeight()/2);
                     AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(value),
                                                                            p.x, p.y);
                     AffineTransformOp atop;
                     atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                     BufferedImage imgdest = atop.filter(imgSource, null);
                     li.setImage(imgdest);
                     li.repaint();
                  }catch(IllegalArgumentException e){
                     System.err.println(e.getLocalizedMessage());
                  }
               }
            }
         });
        
        
        return panel;
    }    

    private JPanel createZoomPanel() {
        JPanel panel = new JPanel();
        JButton zoomIn = new JButton();
        JButton zoomOut = new JButton();
        
        ImageIcon iconIn = new ImageIcon(Lienzo.class.getResource("/com/iris/iconos/aumentar.png"));
        ImageIcon iconOut = new ImageIcon(Lienzo.class.getResource("/com/iris/iconos/disminuir.png"));

        zoomIn.setIcon(iconIn);
        zoomOut.setIcon(iconOut);
        zoomIn.setPreferredSize(new Dimension(32, 32));
        zoomOut.setPreferredSize(new Dimension(32, 32));
        
        // Atributos panel Zoom
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Zoom:"));
        panel.setPreferredSize(new Dimension(110,80));

        panel.add(zoomIn);
        panel.add(zoomOut);
        
        // Listeners
        zoomIn.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt){
               Container vi = desktop.getSelectedFrame().getContentPane();

               if (vi instanceof LienzoImage){
                  LienzoImage li = (LienzoImage) vi;
                  BufferedImage imgSource = li.getFilteredImageRGB();
                  try{
                     Point p = new Point(imgSource.getWidth()/2, imgSource.getHeight()/2);
                     AffineTransform at = AffineTransform.getScaleInstance(1.25,1.25);

                     AffineTransformOp atop;
                     atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                     BufferedImage imgdest = atop.filter(imgSource, null);
                     li.setImage(imgdest);
                     li.repaint();
                  }catch(IllegalArgumentException e){
                     System.err.println(e.getLocalizedMessage());
                  }
               }
            }
         });

        zoomOut.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt){
               Container vi = desktop.getSelectedFrame().getContentPane();

               if (vi instanceof LienzoImage){
                  LienzoImage li = (LienzoImage) vi;
                  BufferedImage imgSource = li.getFilteredImageRGB();
                  try{
                     Point p = new Point(imgSource.getWidth()/2, imgSource.getHeight()/2);
                     AffineTransform at = AffineTransform.getScaleInstance(0.75, 0.75);

                     AffineTransformOp atop;
                     atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                     BufferedImage imgdest = atop.filter(imgSource, null);
                     li.setImage(imgdest);
                     li.repaint();
                  }catch(IllegalArgumentException e){
                     System.err.println(e.getLocalizedMessage());
                  }
               }
            }
         });

        return panel;
    }

    private JPanel createContPanel() {
        JPanel panel = new JPanel();
        GridBagConstraints c;
        JButton normal = new JButton();
        JButton iluminar = new JButton();
        JButton oscurecer = new JButton();
        MyButtonGroup contrasteBtns = new MyButtonGroup();
        JSlider brilloSli = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);

        ImageIcon icon1 = new ImageIcon(Lienzo.class.getResource("/com/iris/iconos/contraste.png"));
        ImageIcon icon2 = new ImageIcon(Lienzo.class.getResource("/com/iris/iconos/iluminar.png"));
        ImageIcon icon3 = new ImageIcon(Lienzo.class.getResource("/com/iris/iconos/oscurecer.png"));

        // Atributos panel rotación
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Contraste:"));
        panel.setPreferredSize(new Dimension(150,100));
        
        brilloSli.setPreferredSize(new Dimension(100,20));
        normal.setIcon(icon1);
        iluminar.setIcon(icon2);
        oscurecer.setIcon(icon3);
        normal.setName("normal");
        iluminar.setName("iluminar");
        oscurecer.setName("oscurecer");
        contrasteBtns.add(normal);
        contrasteBtns.add(iluminar);
        contrasteBtns.add(oscurecer);
        
        // Botón normal
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(normal, c);
        
        // Botón iluminar
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(iluminar, c);
        
        // Botón oscurecer
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(oscurecer, c);
        
        // Slider brillo
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.gridwidth = 3;
        panel.add(brilloSli, c);      
        
        // Listeners
        // Botones de contraste
        for (Enumeration<AbstractButton> buttons = contrasteBtns.getElements();
           buttons.hasMoreElements();){
         final AbstractButton button = buttons.nextElement();

         button.addMouseListener(new MouseAdapter(){
               public void mouseClicked(MouseEvent evt){
                  String botonStr = button.getName();
                  LookupTable lt = null;

                  switch(botonStr){
                  case "normal":
                     lt = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_SFUNCION);
                     break;
                  case "iluminar":
                     lt = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_LOGARITHM);

                     break;
                  case "oscurecer":
                     lt = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_POWER);
                     break;
                  }

                  Container vi = null;
                  if (desktop.getSelectedFrame() != null)
                    vi = desktop.getSelectedFrame().getContentPane();

                  if (vi instanceof LienzoImage){
                     LienzoImage li = (LienzoImage) vi;
                     BufferedImage imgSource = li.getFilteredImageRGB();
                     try{
                        LookupOp lop = new LookupOp(lt, null);
                        BufferedImage imgdest = lop.filter(imgSource, null);
                        li.setImage(imgdest);
                        li.repaint();
                     }catch(IllegalArgumentException e){
                        System.err.println(e.getLocalizedMessage());
                     }
                  }
               }
            });
        }
        
        // Slider
        brilloSli.addChangeListener(new ChangeListener(){
              public void stateChanged(ChangeEvent evt){
                 int value = ((JSlider) evt.getSource()).getValue();
                 Container vi = desktop.getSelectedFrame().getContentPane();

                 if (vi instanceof LienzoImage){
                    LienzoImage li = (LienzoImage) vi;
                    BufferedImage imgSource = li.getFilteredImageRGB();
                    try{
                       value -= li.getBright();
                       RescaleOp rop = new RescaleOp(1.0f, (float) value, null);
                       BufferedImage imgdest = rop.filter(imgSource, null);
                       li.setImage(imgdest);
                       li.setBright(((JSlider) evt.getSource()).getValue());
                       li.repaint();
                    }catch(IllegalArgumentException e){
                       System.err.println(e.getLocalizedMessage());
                    }
                 }
              }
           });
        
        return panel;
    }

    private JPanel createFiltPanel() {
        JPanel panel = new JPanel();
        String [] filters = {"Media", "Binomial", "Enfoque", "Relieve", "Laplaciano"};
        JComboBox tipoFiltro = new JComboBox(filters);
        JButton aplicar = new JButton("Aplicar");

        // Atributos panel Filtros
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Filtros:"));
        panel.setPreferredSize(new Dimension(110,100));
        
        tipoFiltro.setPreferredSize(new Dimension(80, 25));
        aplicar.setPreferredSize(new Dimension(70, 25));
        
        panel.add(tipoFiltro);
        panel.add(aplicar);
        
        return panel;
    }

    private JPanel createUmbPanel() {
        JPanel panel = new JPanel();
        String [] filters = {"Grises", "Colores"};
        JComboBox tipoFiltro = new JComboBox(filters);
        JSlider umbralSli = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
        JButton color = new JButton();
        color.setBackground(Color.red);
        
        // Atributos panel Umbralizar
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Umbralizar:"));
        panel.setPreferredSize(new Dimension(150,140));
        
        panel.add(tipoFiltro);
        panel.add(color);
        panel.add(umbralSli);
        
        
        // Listeners:
        // En el combobox activar/desactivar boton color si es color o grises.

        
        
        
        
        
        
        
        
        
        btnTest = new JButton("Test");
        btnTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (desktop.getSelectedFrame() == null)
                    return;
                if (desktop.getSelectedFrame().getContentPane() instanceof Lienzo)
                    System.out.println("Lienzo");
                if (desktop.getSelectedFrame().getContentPane() instanceof LienzoImage)
                    System.out.println("LienzoImage");
            }
        });
        
        
        return panel;
    }
    
}