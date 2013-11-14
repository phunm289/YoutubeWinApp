/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youtube;

import java.awt.Component;
import java.awt.Cursor;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Wise-SW
 */
public class ViewPlaylist extends javax.swing.JDialog {
//    private String path = System.getenv("user.home") + "playlist";
    private String path = System.getenv("USERPROFILE")+"\\Desktube\\Playlist";
    private TableModel tableModel;
    private File[] files;
    private Desktube parent;
    private String[][] data;
    private Vector<VideoInfo> results;
    /**
     * Creates new form ViewPlaylist
     */
    public ViewPlaylist(java.awt.Frame parent) {
        this.parent = (Desktube) parent;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewPlaylist.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ViewPlaylist.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ViewPlaylist.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ViewPlaylist.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        this.setTitle("Playlist");
        initList();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewPlaylist.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ViewPlaylist.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ViewPlaylist.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ViewPlaylist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        scrollPlaylist = new javax.swing.JScrollPane();
        playlist = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        lblPlaylist = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnPlayAll = new javax.swing.JButton();
        btnPlayOne = new javax.swing.JButton();
        btnDelPl = new javax.swing.JButton();
        btnDelVideo = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblTotalTime = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblPlaylistName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Playlist");
        setMinimumSize(new java.awt.Dimension(700, 390));

        jPanel2.setMinimumSize(new java.awt.Dimension(891, 394));

        scrollPlaylist.setAutoscrolls(true);

        playlist.setBackground(new java.awt.Color(220, 220, 220));
        playlist.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        playlist.setSelectionBackground(new java.awt.Color(250, 250, 250));
        playlist.setSelectionForeground(new java.awt.Color(0, 0, 0));
        playlist.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                playlistValueChanged(evt);
            }
        });
        scrollPlaylist.setViewportView(playlist);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        lblPlaylist.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblPlaylist.setText("PLAYLIST");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(lblPlaylist)
                .addContainerGap(88, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblPlaylist)
                .addContainerGap())
        );

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setBackground(new java.awt.Color(250, 250, 250));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "title1", "title", "title", "title"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane1.setViewportView(jTable1);

        btnPlayAll.setText("Play All");
        btnPlayAll.setMaximumSize(new java.awt.Dimension(57, 33));
        btnPlayAll.setMinimumSize(new java.awt.Dimension(57, 33));
        btnPlayAll.setPreferredSize(new java.awt.Dimension(57, 33));
        btnPlayAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayAllActionPerformed(evt);
            }
        });

        btnPlayOne.setText("Play Video");
        btnPlayOne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayOneActionPerformed(evt);
            }
        });

        btnDelPl.setText("Delete Playlist");
        btnDelPl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelPlActionPerformed(evt);
            }
        });

        btnDelVideo.setText("Delete Video");
        btnDelVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelVideoActionPerformed(evt);
            }
        });

        jLabel1.setText("Total Time:");

        lblTotalTime.setToolTipText("");

        jLabel2.setText("Playlist Name:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnPlayAll, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPlayOne)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPlaylistName)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalTime)
                        .addGap(30, 30, 30)
                        .addComponent(btnDelPl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelVideo)
                        .addGap(45, 45, 45))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(scrollPlaylist, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPlayOne)
                            .addComponent(btnDelPl)
                            .addComponent(btnDelVideo)
                            .addComponent(btnPlayAll, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(lblTotalTime)
                            .addComponent(jLabel2)
                            .addComponent(lblPlaylistName))
                        .addGap(15, 15, 15))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void playlistValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_playlistValueChanged
        // TODO add your handling code here:
        if(!playlist.getValueIsAdjusting()){
            initTable();
        }
        
    }//GEN-LAST:event_playlistValueChanged

    private void btnPlayAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayAllActionPerformed
        // TODO add your handling code here:
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        int index = playlist.getSelectedIndex();
        if(files.length > 0){
            String path_ = files[index].getAbsolutePath();
            if(parent != null){
                results = xmlDecoder(path_);

                parent.setResults(results);
                parent.loadVideoDataToPanel();
                parent.loadSearchVideoToList(0);
                parent.loadPlayerToPanel();
                parent.setPlayerType(true);
                parent.playVideo(parent.getResultList().getSelectedIndex());
                parent.hidePageIndex();
            }
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnPlayAllActionPerformed
    private void btnPlayOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayOneActionPerformed
        // TODO add your handling code here:
        int[] indices = jTable1.getSelectedRows();
        if( results != null && results.size() > 0)
        if(indices.length > 0 && indices.length == 1){
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            VideoInfo result = results.get(jTable1.getSelectedRow());
            results = new Vector<>();
            results.add(result);
            if(parent != null){
                parent.setResults(results);
                parent.loadVideoDataToPanel();
                parent.loadSearchVideoToList(0);
                parent.loadPlayerToPanel();
                parent.playVideo(parent.getResultList().getSelectedIndex());
                parent.setPlayerType(true);
                parent.hidePageIndex();
            }
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        if(indices.length > 1){
            int index = playlist.getSelectedIndex();
            
            String path_ = files[index].getAbsolutePath();
            if(parent != null){
                results = xmlDecoder(path_);
                Vector<VideoInfo> subResults = new Vector<>();
                for(int i = 0; i < indices.length; i++){
                    subResults.add(results.get(indices[i]));
                }
                
                parent.setResults(subResults);
                parent.loadVideoDataToPanel();
                parent.loadSearchVideoToList(0);
                parent.loadPlayerToPanel();
                parent.playVideo(parent.getResultList().getSelectedIndex());
                parent.setPlayerType(true);
//                parent.hidePageIndex();
            } 
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnPlayOneActionPerformed

    private void btnDelPlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelPlActionPerformed
        // TODO add your handling code here:
        if (playlist.getLastVisibleIndex() >= 0) {
            int option = JOptionPane.showConfirmDialog(this, "Do you realy want to delete this playlist?");
            if (option == JOptionPane.YES_OPTION) {
                if (playlist.getFirstVisibleIndex() == playlist.getLastVisibleIndex()) {
                    setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    int indices = jTable1.getRowCount();
                    int ppIndex = playlist.getSelectedIndex();
                    String path = this.path + "\\" + files[ppIndex].getName();
                    Vector<VideoInfo> vector = xmlDecoder(path);
                    for (int i = 0; i < indices; i++) {
                        vector.remove(0);
                    }
                    xmlEncoder(path, vector);
                    initTable();
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                int index = playlist.getSelectedIndex();
                File file = files[index];
                if (file != null) {
                    if (file.delete()) {
                        initList();
                        playlist.revalidate();
                        JOptionPane.showMessageDialog(this, "Deleted Playlist!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Cannot delete playlist.");
                    }
                }
            }
        }
    }//GEN-LAST:event_btnDelPlActionPerformed

    private void btnDelVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelVideoActionPerformed
        // TODO add your handling code here:
        if (results != null && results.size() >= 0 && jTable1.getSelectedRows().length > 0) {
            int chooser = JOptionPane.showConfirmDialog(this, "Do you really want to delete videos?");
            if (chooser == JOptionPane.YES_OPTION) {
                int[] indices = jTable1.getSelectedRows();
                if (indices.length > 0 && indices.length == 1) {
                    setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    int ppIndex = playlist.getSelectedIndex();
                    String path = this.path + "\\" + files[ppIndex].getName();
                    Vector<VideoInfo> vector = xmlDecoder(path);
                    for (int i = 0; i < indices.length; i++) {
                        vector.remove(indices[i]);
                    }
                    xmlEncoder(path, vector);
                    initTable();
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                if (indices.length > 1) {
                    setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    int ppIndex = playlist.getSelectedIndex();
                    String path = this.path + "\\" + files[ppIndex].getName();
                    Vector<VideoInfo> vector = xmlDecoder(path);
                    for (int i = 0; i < indices.length; i++) {
                        vector.remove(indices[i] - i);
                    }
                    xmlEncoder(path, vector);
                    initTable();
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        }
    }//GEN-LAST:event_btnDelVideoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelPl;
    private javax.swing.JButton btnDelVideo;
    private javax.swing.JButton btnPlayAll;
    private javax.swing.JButton btnPlayOne;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblPlaylist;
    private javax.swing.JLabel lblPlaylistName;
    private javax.swing.JLabel lblTotalTime;
    private javax.swing.JList playlist;
    private javax.swing.JScrollPane scrollPlaylist;
    // End of variables declaration//GEN-END:variables

    private void initList() {
        File folder = new File(path);
        files = folder.listFiles();
        String[] playlistName = new String[files.length];
        for(int i = 0; i < files.length; i++){
            playlistName[i] = files[i].getName();
        }
        
        playlist.setListData(playlistName);
        playlist.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setIcon(new ImageIcon(getClass().getResource("/ToolIcon/Video_Playlist.png")));
                return label;
            }
        });
        scrollPlaylist.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        playlist.setSelectedIndex(0);
    }

    private void initTable() {
        tableModel = new DefaultTableModel(setData(), setColumnName()){
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex) {
            return false;
          }};
        jTable1.setModel(tableModel);
        jTable1.revalidate();
        
        if(playlist.getSelectedIndex() >= 0){
            Vector<VideoInfo> vector = xmlDecoder(path+"\\"+files[playlist.getSelectedIndex()].getName());
            int totalSec = 0;
            for(VideoInfo result : vector){
                String time = result.getTime();
                Boolean hour = false;
                int index = time.indexOf(":",0);
                if(index >= 0){
                    String behind = time.substring(index+1,time.length());
                    int index2 = behind.indexOf(":", index);
                    if(index2 >= 0){
                        hour = true;
                    }
                    if(!hour){
                        int sec = Integer.parseInt(time.substring(0, index))*60 + Integer.parseInt(time.substring(index+1, time.length()));
                        totalSec+=sec;
                    }else{
                        int sec = Integer.parseInt(time.substring(0, index))*3600 + Integer.parseInt(time.substring(index+1, index2+index+1))*60+Integer.parseInt(time.substring(index2+index+2, time.length()));
                        totalSec+=sec;
                    }
                }
            }
            if(totalSec < 3600){
                int min = (int)(totalSec / 60);
                int sec = totalSec % 60;
                lblTotalTime.setText((min < 10 ? "0"+min : min)+" : "+(sec < 10 ? "0"+sec : sec));
            }
            else if(totalSec >= 3600){
                int hour = (int)(totalSec / 3600);
                int min = (totalSec % 3600) / 60;
                int sec = (totalSec % 3600) % 60;
                lblTotalTime.setText((hour < 10 ? "0"+hour : hour)+" : "+(min < 10 ? "0"+min : min)+" : "+(sec < 10 ? "0"+sec : sec));
            }
        }
    }

    private String[] setColumnName() {
        String[] columnName = new String[4];
        columnName[0] = "Name";
        columnName[1] = "Time";
        columnName[2] = "Uploader";
        columnName[3] = "View Count";
        return columnName;
    }

    private String[][] setData() {
            if(playlist.getSelectedIndex() >= 0){
            String playlistName = files[playlist.getSelectedIndex()].getName();
            results = xmlDecoder(path+"\\"+playlistName);
            if(true){
                data = new String[results.size()][4];
                for(int i = 0; i < results.size(); i++){
                    String[] data_temp = {results.get(i).getTitle(),results.get(i).getTime(),results.get(i).getUploadUser(),results.get(i).getViewCount()};
                    data[i] = data_temp;
                }
            }
        }
        return data;
    }
    private void xmlEncoder(String path, Vector<VideoInfo> vector) {
        try {
            XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path)));
            encoder.writeObject(vector);
            encoder.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private Vector<VideoInfo> xmlDecoder(String path){
        Vector<VideoInfo> playlist = new Vector<>();
        XMLDecoder decoder;
        try {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
            try{
            playlist = (Vector<VideoInfo>)decoder.readObject();
            }catch(Exception e){

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ViewPlaylist.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return playlist;
    }

}
