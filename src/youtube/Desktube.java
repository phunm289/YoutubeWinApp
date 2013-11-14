 package youtube;                                     
                                    
import com.sun.jna.NativeLibrary;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.IconUIResource;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import org.json.JSONObject;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;


public class Desktube extends JFrame {

    
    private HttpClient httpclient;
    private HttpGet httpget;
    private ResponseHandler<String> responseHandler;
    private Vector<VideoInfo> videoResults;
    private Vector<ChannelInfo> channelResults;
    private Vector<PlaylistInfo> playlistResults;
    private JList resultList;
    private JScrollPane scrollResultList;
    private Thread search;
    private EmbeddedMediaPlayerComponent embededMPC;
    private HoverListCellRenderer renderer;
    private JPanel[] dataPanels;
    private String query;
    private int pageIndex = 1;
    private JPanel pnBrowser;
    private Vector<VideoInfo> playlist;
    private int countFScreen = 1;
    private JFrame fullscreenFrame = null;
    private String desktubeDoc = System.getenv("USERPROFILE") + "\\Desktube";
    private String playlistPath = desktubeDoc + "\\Playlist";
    private String subPath = desktubeDoc + "\\Sub\\subtitle.srt";
    int countBtnListPerformed = 1;
    int countVol = 1;
    private Icon iconVol;
    private ViewPlaylist vpl = null;
    private BorderLayout borderLayout = new BorderLayout();
    private int countBtnShow = 1;
    private Vector<VideoInfo> new_VideoInfo;
    private JPanel pnChannel;
    private JScrollPane scrChannel;
    private String lang = "";
    private String video_id = "";
    private int playing_video_index = 0;
    private JComboBox usingCombobox;
    private int currentVol = 0;
    static {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), System.getenv("user.dir"));
    }

    public static void main(String[] args) {
        Desktube y = new Desktube();
        y.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        y.setVisible(true);
    }

    public JList getResultList() {
        return resultList;
    }

    public Vector<VideoInfo> getResults() {
        return videoResults;
    }

    public void setResults(Vector<VideoInfo> results) {
        this.videoResults = results;
    }

    public Desktube() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            IconUIResource specialThumbIcon = new IconUIResource(new SpecialThumbIcon());
            UIManager.put("Slider.horizontalThumbIcon", specialThumbIcon);
            UIManager.put("Slider.verticalThumbIcon", specialThumbIcon);
            UIManager.put("Slider.background", new Color(237, 237, 237));
            UIManager.put("Slider.trackWidth", 1);
            UIManager.put("Slider.majorTickLength", 1);
            UIManager.put("ScrollBar.width",new Integer(15) );
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        ImageIcon img = new ImageIcon(getClass().getResource("/ToolIcon/MiniDTLogo.png"));
        setIconImage(img.getImage());
        setLocation(100, 100);
        txtSearchDef.requestFocus();
        setPlayerType(false);
        iconVol = lbVolume.getIcon();
        if (!new File(desktubeDoc).isDirectory()) {
            new File(desktubeDoc).mkdir();
        }
        if (!new File(playlistPath).isDirectory()) {
            new File(playlistPath).mkdir();
        }
        if (!new File(desktubeDoc + "\\Sub").isDirectory()) {
            new File(desktubeDoc + "\\Sub").mkdir();
        }
        lblBackPage.setVisible(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        popupMenu = new javax.swing.JPopupMenu();
        miShowThis = new javax.swing.JMenuItem();
        miSaveThis = new javax.swing.JMenuItem();
        pnContent = new javax.swing.JPanel();
        pnStatus = new javax.swing.JPanel();
        LblTitleVideo = new javax.swing.JLabel();
        lbPage = new javax.swing.JLabel();
        lblPre = new javax.swing.JLabel();
        lblFPage = new javax.swing.JLabel();
        lblNext = new javax.swing.JLabel();
        lblLPage = new javax.swing.JLabel();
        btnOrder = new javax.swing.JToggleButton();
        btnRepeat = new javax.swing.JToggleButton();
        lblBackPage = new javax.swing.JLabel();
        pnTop = new javax.swing.JPanel();
        pnTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        pnControl = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnStop = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btnPlayPause = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnList = new javax.swing.JButton();
        lblSearch = new javax.swing.JLabel();
        btnFullS = new javax.swing.JButton();
        sliderSeek = new javax.swing.JSlider();
        sliderVol = new javax.swing.JSlider();
        lblSecond = new javax.swing.JLabel();
        lblTotalTime = new javax.swing.JLabel();
        lbVolume = new javax.swing.JLabel();
        lblMinute = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblHour = new javax.swing.JLabel();
        lblDBDot = new javax.swing.JLabel();
        cbbFilter2 = new javax.swing.JComboBox();
        lblVolPercent = new javax.swing.JLabel();
        pnResult = new BackgroundPanel();
        pnDefault = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        lblSearchDef = new javax.swing.JLabel();
        txtSearchDef = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbbFilter = new javax.swing.JComboBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnFile = new javax.swing.JMenu();
        miHome = new javax.swing.JMenuItem();
        cbmiAlwaysOnTop = new javax.swing.JCheckBoxMenuItem();
        mnPlayback = new javax.swing.JMenu();
        miStop = new javax.swing.JMenuItem();
        miPP = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miNext = new javax.swing.JMenuItem();
        miPrev = new javax.swing.JMenuItem();
        miShuffle = new javax.swing.JCheckBoxMenuItem();
        miRepeat = new javax.swing.JCheckBoxMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        miStopAfter = new javax.swing.JCheckBoxMenuItem();
        miExitAfter = new javax.swing.JCheckBoxMenuItem();
        mSubLang = new javax.swing.JMenu();
        rdbEng = new javax.swing.JRadioButtonMenuItem();
        rdbMIViet = new javax.swing.JRadioButtonMenuItem();
        rdbKorea = new javax.swing.JRadioButtonMenuItem();
        rdbGerman = new javax.swing.JRadioButtonMenuItem();
        rdbRussian = new javax.swing.JRadioButtonMenuItem();
        rdbOff = new javax.swing.JRadioButtonMenuItem();
        jmnPlaylist = new javax.swing.JMenu();
        mAddVideo = new javax.swing.JMenu();
        miNewPl = new javax.swing.JMenuItem();
        miExistedPl = new javax.swing.JMenuItem();
        miAddAll = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        miViewPlaylist = new javax.swing.JMenuItem();

        miShowThis.setText("Show this playlist");
        miShowThis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miShowThisActionPerformed(evt);
            }
        });
        popupMenu.add(miShowThis);

        miSaveThis.setText("Save this playlist");
        miSaveThis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSaveThisActionPerformed(evt);
            }
        });
        popupMenu.add(miSaveThis);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Desktube");
        setMinimumSize(new java.awt.Dimension(1257, 681));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        pnContent.setMinimumSize(new java.awt.Dimension(640, 480));
        pnContent.setLayout(new java.awt.BorderLayout());

        lbPage.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        lblPre.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblPre.setToolTipText("Previous Page");
        lblPre.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPreMouseClicked(evt);
            }
        });

        lblFPage.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblFPage.setToolTipText("First Page");
        lblFPage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblFPage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFPageMouseClicked(evt);
            }
        });

        lblNext.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblNext.setToolTipText("Next Page");
        lblNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNextMouseClicked(evt);
            }
        });

        lblLPage.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblLPage.setToolTipText("Last Page");
        lblLPage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLPage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLPageMouseClicked(evt);
            }
        });

        btnOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/shuffle.png"))); // NOI18N
        btnOrder.setToolTipText("Turn shuffle on");
        btnOrder.setActionCommand("orderPlayer");
        btnOrder.setFocusPainted(false);
        btnOrder.setMaximumSize(new java.awt.Dimension(24, 24));
        btnOrder.setMinimumSize(new java.awt.Dimension(24, 24));
        btnOrder.setPreferredSize(new java.awt.Dimension(24, 24));
        btnOrder.setRequestFocusEnabled(false);
        btnOrder.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/selected_shuffle.png"))); // NOI18N
        btnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderActionPerformed(evt);
            }
        });

        btnRepeat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/repeat.png"))); // NOI18N
        btnRepeat.setToolTipText("Turn repeat on");
        btnRepeat.setActionCommand("repeat");
        btnRepeat.setFocusPainted(false);
        btnRepeat.setMaximumSize(new java.awt.Dimension(24, 24));
        btnRepeat.setMinimumSize(new java.awt.Dimension(24, 24));
        btnRepeat.setPreferredSize(new java.awt.Dimension(24, 24));
        btnRepeat.setRequestFocusEnabled(false);
        btnRepeat.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/selected_repeat.png"))); // NOI18N
        btnRepeat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRepeatActionPerformed(evt);
            }
        });

        lblBackPage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/before.png"))); // NOI18N
        lblBackPage.setToolTipText("Back");
        lblBackPage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblBackPage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBackPageMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnStatusLayout = new javax.swing.GroupLayout(pnStatus);
        pnStatus.setLayout(pnStatusLayout);
        pnStatusLayout.setHorizontalGroup(
            pnStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LblTitleVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 280, Short.MAX_VALUE)
                .addComponent(lblFPage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbPage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLPage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBackPage)
                .addGap(18, 18, 18)
                .addComponent(btnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130))
        );
        pnStatusLayout.setVerticalGroup(
            pnStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStatusLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(pnStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(LblTitleVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbPage)
                    .addComponent(lblPre)
                    .addComponent(lblFPage)
                    .addComponent(lblNext)
                    .addComponent(lblLPage)
                    .addComponent(btnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBackPage))
                .addGap(3, 3, 3))
        );

        pnContent.add(pnStatus, java.awt.BorderLayout.SOUTH);

        pnTop.setForeground(new java.awt.Color(78, 78, 80));
        pnTop.setLayout(new java.awt.BorderLayout());

        pnTitle.setBackground(new java.awt.Color(65, 65, 65));
        pnTitle.setForeground(new java.awt.Color(255, 255, 255));

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Desktube");
        lblTitle.setToolTipText("");
        lblTitle.setMaximumSize(new java.awt.Dimension(0, 0));
        lblTitle.setMinimumSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout pnTitleLayout = new javax.swing.GroupLayout(pnTitle);
        pnTitle.setLayout(pnTitleLayout);
        pnTitleLayout.setHorizontalGroup(
            pnTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
        );
        pnTitleLayout.setVerticalGroup(
            pnTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTitleLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pnTop.add(pnTitle, java.awt.BorderLayout.SOUTH);

        pnControl.setBackground(new java.awt.Color(237, 237, 237));

        txtSearch.setMaximumSize(new java.awt.Dimension(100, 22));
        txtSearch.setMinimumSize(new java.awt.Dimension(0, 0));
        txtSearch.setPreferredSize(new java.awt.Dimension(100, 22));
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/1369380571_stop.png"))); // NOI18N
        btnStop.setToolTipText("Stop video");
        btnStop.setBorder(null);
        btnStop.setContentAreaFilled(false);
        btnStop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStop.setEnabled(false);
        btnStop.setFocusPainted(false);
        btnStop.setMaximumSize(new java.awt.Dimension(48, 48));
        btnStop.setMinimumSize(new java.awt.Dimension(0, 0));
        btnStop.setPreferredSize(new java.awt.Dimension(48, 48));
        btnStop.setRequestFocusEnabled(false);
        btnStop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnStopMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnStopMouseExited(evt);
            }
        });
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        btnPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/1370252499_first.png"))); // NOI18N
        btnPrevious.setToolTipText("Previous Video");
        btnPrevious.setBorder(null);
        btnPrevious.setContentAreaFilled(false);
        btnPrevious.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrevious.setEnabled(false);
        btnPrevious.setFocusPainted(false);
        btnPrevious.setMaximumSize(new java.awt.Dimension(48, 48));
        btnPrevious.setMinimumSize(new java.awt.Dimension(0, 0));
        btnPrevious.setPreferredSize(new java.awt.Dimension(48, 48));
        btnPrevious.setRequestFocusEnabled(false);
        btnPrevious.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPreviousMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPreviousMouseExited(evt);
            }
        });
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btnPlayPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/1369380612_pause.png"))); // NOI18N
        btnPlayPause.setToolTipText("Pause video");
        btnPlayPause.setBorder(null);
        btnPlayPause.setContentAreaFilled(false);
        btnPlayPause.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPlayPause.setEnabled(false);
        btnPlayPause.setFocusPainted(false);
        btnPlayPause.setMaximumSize(new java.awt.Dimension(48, 48));
        btnPlayPause.setMinimumSize(new java.awt.Dimension(0, 0));
        btnPlayPause.setPreferredSize(new java.awt.Dimension(48, 48));
        btnPlayPause.setRequestFocusEnabled(false);
        btnPlayPause.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPlayPauseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPlayPauseMouseExited(evt);
            }
        });
        btnPlayPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayPauseActionPerformed(evt);
            }
        });

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/1370252543_last.png"))); // NOI18N
        btnNext.setToolTipText("Next video");
        btnNext.setBorder(null);
        btnNext.setContentAreaFilled(false);
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNext.setEnabled(false);
        btnNext.setFocusPainted(false);
        btnNext.setMaximumSize(new java.awt.Dimension(48, 48));
        btnNext.setMinimumSize(new java.awt.Dimension(0, 0));
        btnNext.setPreferredSize(new java.awt.Dimension(48, 48));
        btnNext.setRequestFocusEnabled(false);
        btnNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNextMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNextMouseExited(evt);
            }
        });
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/1369380829_list.png"))); // NOI18N
        btnList.setToolTipText("Hide/Show List Result");
        btnList.setBorder(null);
        btnList.setContentAreaFilled(false);
        btnList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnList.setEnabled(false);
        btnList.setFocusPainted(false);
        btnList.setMaximumSize(new java.awt.Dimension(48, 48));
        btnList.setMinimumSize(new java.awt.Dimension(0, 0));
        btnList.setPreferredSize(new java.awt.Dimension(48, 48));
        btnList.setRequestFocusEnabled(false);
        btnList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnListMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnListMouseExited(evt);
            }
        });
        btnList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListActionPerformed(evt);
            }
        });

        lblSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/1369384512_magnifying_glass.png"))); // NOI18N
        lblSearch.setMinimumSize(new java.awt.Dimension(0, 0));

        btnFullS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/fullscreen.png"))); // NOI18N
        btnFullS.setToolTipText("Full Screen");
        btnFullS.setBorder(null);
        btnFullS.setContentAreaFilled(false);
        btnFullS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFullS.setFocusPainted(false);
        btnFullS.setMaximumSize(new java.awt.Dimension(48, 48));
        btnFullS.setMinimumSize(new java.awt.Dimension(0, 0));
        btnFullS.setPreferredSize(new java.awt.Dimension(48, 48));
        btnFullS.setRequestFocusEnabled(false);
        btnFullS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnFullSMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnFullSMouseExited(evt);
            }
        });
        btnFullS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFullSActionPerformed(evt);
            }
        });

        sliderSeek.setUI(new youtube.MySliderUI());
        sliderSeek.setValue(0);
        sliderSeek.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sliderSeek.setMinimumSize(new java.awt.Dimension(0, 0));
        sliderSeek.setOpaque(false);
        sliderSeek.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderSeekStateChanged(evt);
            }
        });

        sliderVol.setUI(new youtube.MySliderUI());
        sliderVol.setMaximum(200);
        sliderVol.setValue(100);
        sliderVol.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sliderVol.setMinimumSize(new java.awt.Dimension(0, 0));
        sliderVol.setOpaque(false);
        sliderVol.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderVolStateChanged(evt);
            }
        });

        lblSecond.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        lblSecond.setText("00");
        lblSecond.setMinimumSize(new java.awt.Dimension(0, 0));

        lblTotalTime.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        lblTotalTime.setText("00 : 00");
        lblTotalTime.setMinimumSize(new java.awt.Dimension(0, 0));

        lbVolume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/audio_hight.png"))); // NOI18N
        lbVolume.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbVolume.setMinimumSize(new java.awt.Dimension(0, 0));
        lbVolume.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbVolumeMouseClicked(evt);
            }
        });

        lblMinute.setText("00");
        lblMinute.setMinimumSize(new java.awt.Dimension(0, 0));

        jLabel3.setText(":");
        jLabel3.setMinimumSize(new java.awt.Dimension(0, 0));

        cbbFilter2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Video", "Channel", "Playlist" }));
        cbbFilter2.setRequestFocusEnabled(false);
        cbbFilter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbFilter2ActionPerformed(evt);
            }
        });

        lblVolPercent.setText(sliderVol.getValue()+"%");
        lblVolPercent.setText("100%");
        lblVolPercent.setPreferredSize(new java.awt.Dimension(30, 14));
        lblVolPercent.setRequestFocusEnabled(false);
        lblVolPercent.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout pnControlLayout = new javax.swing.GroupLayout(pnControl);
        pnControl.setLayout(pnControlLayout);
        pnControlLayout.setHorizontalGroup(
            pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnControlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnPlayPause, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnList, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnFullS, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHour)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDBDot)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSecond, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(sliderSeek, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sliderVol, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblVolPercent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbFilter2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        pnControlLayout.setVerticalGroup(
            pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnStop, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(cbbFilter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(lblSearch, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(sliderSeek, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lblVolPercent, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(sliderVol, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lblTotalTime, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lbVolume, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lblSecond, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(lblMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPlayPause, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnPrevious, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnNext, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnList, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnFullS, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHour, javax.swing.GroupLayout.Alignment.CENTER)))
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lblDBDot, javax.swing.GroupLayout.Alignment.CENTER)
        );

        pnTop.add(pnControl, java.awt.BorderLayout.CENTER);

        pnContent.add(pnTop, java.awt.BorderLayout.NORTH);

        pnResult.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnResultMouseClicked(evt);
            }
        });

        pnDefault.setOpaque(false);
        pnDefault.setPreferredSize(new java.awt.Dimension(400, 200));
        pnDefault.setLayout(null);

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/Desk-tube.png"))); // NOI18N
        pnDefault.add(logo);
        logo.setBounds(10, 10, 128, 128);

        lblWelcome.setBackground(new java.awt.Color(255, 255, 255));
        lblWelcome.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(78, 78, 78));
        lblWelcome.setText("Desk-Tube");
        pnDefault.add(lblWelcome);
        lblWelcome.setBounds(264, 11, 75, 17);

        lblSearchDef.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/1369384512_magnifying_glass.png"))); // NOI18N
        pnDefault.add(lblSearchDef);
        lblSearchDef.setBounds(144, 60, 24, 24);

        txtSearchDef.setText("Larva");
        txtSearchDef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchDefActionPerformed(evt);
            }
        });
        pnDefault.add(txtSearchDef);
        txtSearchDef.setBounds(172, 62, 261, 20);

        btnSearch.setText("Search");
        btnSearch.setOpaque(false);
        btnSearch.setRequestFocusEnabled(false);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        pnDefault.add(btnSearch);
        btnSearch.setBounds(190, 120, 65, 23);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(78, 78, 78));
        jLabel1.setText("Enter a words to start searching video:");
        pnDefault.add(jLabel1);
        jLabel1.setBounds(148, 34, 225, 15);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(78, 78, 78));
        jLabel2.setText("Welcome to");
        pnDefault.add(jLabel2);
        jLabel2.setBounds(184, 10, 74, 17);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setForeground(new java.awt.Color(78, 78, 78));
        jLabel6.setText("Filter by:");
        pnDefault.add(jLabel6);
        jLabel6.setBounds(144, 98, 43, 14);

        cbbFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Video", "Channel", "Playlist" }));
        cbbFilter.setSelectedIndex(2);
        cbbFilter.setLightWeightPopupEnabled(false);
        cbbFilter.setOpaque(false);
        cbbFilter.setRequestFocusEnabled(false);
        cbbFilter.setVerifyInputWhenFocusTarget(false);
        cbbFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbFilterActionPerformed(evt);
            }
        });
        pnDefault.add(cbbFilter);
        cbbFilter.setBounds(197, 95, 101, 20);

        javax.swing.GroupLayout pnResultLayout = new javax.swing.GroupLayout(pnResult);
        pnResult.setLayout(pnResultLayout);
        pnResultLayout.setHorizontalGroup(
            pnResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnResultLayout.createSequentialGroup()
                .addContainerGap(419, Short.MAX_VALUE)
                .addComponent(pnDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(420, Short.MAX_VALUE))
        );
        pnResultLayout.setVerticalGroup(
            pnResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnResultLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(pnDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(172, Short.MAX_VALUE))
        );

        pnContent.add(pnResult, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnContent, java.awt.BorderLayout.CENTER);

        mnFile.setMnemonic('F');
        mnFile.setText("File");

        miHome.setText("Back Home");
        miHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miHomeActionPerformed(evt);
            }
        });
        mnFile.add(miHome);

        cbmiAlwaysOnTop.setMnemonic('T');
        cbmiAlwaysOnTop.setText("Alway On Top");
        cbmiAlwaysOnTop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbmiAlwaysOnTopActionPerformed(evt);
            }
        });
        mnFile.add(cbmiAlwaysOnTop);

        jMenuBar1.add(mnFile);

        mnPlayback.setMnemonic('b');
        mnPlayback.setText("Playback");
        mnPlayback.setEnabled(false);

        miStop.setMnemonic('t');
        miStop.setText("Stop Video");
        miStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miStopActionPerformed(evt);
            }
        });
        mnPlayback.add(miStop);

        miPP.setMnemonic('s');
        miPP.setText("Pause Video");
        miPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPPActionPerformed(evt);
            }
        });
        mnPlayback.add(miPP);
        mnPlayback.add(jSeparator1);

        miNext.setMnemonic('N');
        miNext.setText("Next Video");
        miNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNextActionPerformed(evt);
            }
        });
        mnPlayback.add(miNext);

        miPrev.setMnemonic('v');
        miPrev.setText("Previous Video");
        miPrev.setToolTipText("r");
        miPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPrevActionPerformed(evt);
            }
        });
        mnPlayback.add(miPrev);

        miShuffle.setText("Turn shuffle on");
        miShuffle.setEnabled(false);
        miShuffle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miShuffleActionPerformed(evt);
            }
        });
        mnPlayback.add(miShuffle);

        miRepeat.setText("Turn repeat On");
        miRepeat.setEnabled(false);
        miRepeat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miRepeatActionPerformed(evt);
            }
        });
        mnPlayback.add(miRepeat);
        mnPlayback.add(jSeparator2);

        miStopAfter.setMnemonic('f');
        miStopAfter.setText("Stop after this video");
        mnPlayback.add(miStopAfter);

        miExitAfter.setMnemonic('x');
        miExitAfter.setText("Exit after this video");
        mnPlayback.add(miExitAfter);

        mSubLang.setText("Subtitle Language");

        buttonGroup1.add(rdbEng);
        rdbEng.setMnemonic('E');
        rdbEng.setText("English");
        rdbEng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbEngActionPerformed(evt);
            }
        });
        mSubLang.add(rdbEng);
        rdbEng.getAccessibleContext().setAccessibleName("");

        buttonGroup1.add(rdbMIViet);
        rdbMIViet.setMnemonic('V');
        rdbMIViet.setText("Vietnamese");
        rdbMIViet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbMIVietActionPerformed(evt);
            }
        });
        mSubLang.add(rdbMIViet);
        rdbMIViet.getAccessibleContext().setAccessibleName("");

        buttonGroup1.add(rdbKorea);
        rdbKorea.setMnemonic('K');
        rdbKorea.setText("Korean");
        rdbKorea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbKoreaActionPerformed(evt);
            }
        });
        mSubLang.add(rdbKorea);
        rdbKorea.getAccessibleContext().setAccessibleName("");

        buttonGroup1.add(rdbGerman);
        rdbGerman.setMnemonic('G');
        rdbGerman.setText("German");
        rdbGerman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbGermanActionPerformed(evt);
            }
        });
        mSubLang.add(rdbGerman);
        rdbGerman.getAccessibleContext().setAccessibleName("");

        buttonGroup1.add(rdbRussian);
        rdbRussian.setMnemonic('R');
        rdbRussian.setText("Russian");
        rdbRussian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbRussianActionPerformed(evt);
            }
        });
        mSubLang.add(rdbRussian);
        rdbRussian.getAccessibleContext().setAccessibleName("");

        buttonGroup1.add(rdbOff);
        rdbOff.setSelected(true);
        rdbOff.setText("Turn Off Subtitles");
        rdbOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbOffActionPerformed(evt);
            }
        });
        mSubLang.add(rdbOff);

        mnPlayback.add(mSubLang);

        jMenuBar1.add(mnPlayback);

        jmnPlaylist.setMnemonic('P');
        jmnPlaylist.setText("Playlist");

        mAddVideo.setText("Add Video");
        mAddVideo.setEnabled(false);

        miNewPl.setText("New Playlist");
        miNewPl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNewPlActionPerformed(evt);
            }
        });
        mAddVideo.add(miNewPl);

        miExistedPl.setText("Existed Playlist");
        miExistedPl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExistedPlActionPerformed(evt);
            }
        });
        mAddVideo.add(miExistedPl);

        jmnPlaylist.add(mAddVideo);

        miAddAll.setText("Add all results");
        miAddAll.setEnabled(false);
        miAddAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAddAllActionPerformed(evt);
            }
        });
        jmnPlaylist.add(miAddAll);
        jmnPlaylist.add(jSeparator3);

        miViewPlaylist.setText("View Playlist");
        miViewPlaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miViewPlaylistActionPerformed(evt);
            }
        });
        jmnPlaylist.add(miViewPlaylist);

        jMenuBar1.add(jmnPlaylist);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        secondarySearching();
    }//GEN-LAST:event_txtSearchActionPerformed
    int btnPPCount = 1;
    private void btnPlayPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayPauseActionPerformed
        doPlayPauseVideo();
    }//GEN-LAST:event_btnPlayPauseActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        stopVideo();
    }//GEN-LAST:event_btnStopActionPerformed
    private void txtSearchDefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchDefActionPerformed
        
        searching();
    }//GEN-LAST:event_txtSearchDefActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        searching();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnFullSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFullSActionPerformed
        doFullSscreen();
    }//GEN-LAST:event_btnFullSActionPerformed

    @SuppressWarnings("empty-statement")
    private void sliderVolStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderVolStateChanged
        // TODO add your handling code here:
        if (embededMPC != null) {
            embededMPC.getMediaPlayer().setVolume(sliderVol.getValue());;
        }
        if (sliderVol.getValueIsAdjusting()) {
            countVol = 1;
            if (sliderVol.getValue() == 0) {
                lbVolume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/audio_mute.png")));
                iconVol = lbVolume.getIcon();
            }
            if (sliderVol.getValue() > 0 && sliderVol.getValue() <= 40) {
                lbVolume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/audio_low.png")));
                iconVol = lbVolume.getIcon();
            }
            if (sliderVol.getValue() > 40 && sliderVol.getValue() <= 80) {
                lbVolume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/audio_medium.png")));
                iconVol = lbVolume.getIcon();
            }
            if (sliderVol.getValue() > 80 && sliderVol.getValue() <= 200) {
                lbVolume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ToolIcon/audio_hight.png")));
                iconVol = lbVolume.getIcon();
            }
            if (embededMPC != null) {
                embededMPC.getMediaPlayer().mute(false);
            }
            lblVolPercent.setText(sliderVol.getValue() + "%");
        }
    }//GEN-LAST:event_sliderVolStateChanged

    private void sliderSeekStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderSeekStateChanged
        // TODO add your handling code here:
        if (embededMPC != null) {
            if (sliderSeek.getValueIsAdjusting()) {
                embededMPC.getMediaPlayer().setTime(sliderSeek.getValue());
                int time = (int) ((int) (embededMPC.getMediaPlayer().getTime()) / 1000);
                setTimer(time);
            }
        }

    }//GEN-LAST:event_sliderSeekStateChanged

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        playNextVideo();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListActionPerformed
        // TODO add your handling code here:
        countBtnListPerformed++;
        if (scrollResultList != null) {
            if (countBtnListPerformed % 2 == 0) {
                hideList();
                hidePageIndex();
            } else {
                showList();
                showPageIndex();
            }
        }
    }//GEN-LAST:event_btnListActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        playPrevVideo();
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void lblPreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPreMouseClicked
        // TODO add your handling code here:
        if (pageIndex > 1) {
            pageIndex--;
            search = new Thread(new Runnable() {
                @Override
                public void run() {
                    paging(usingCombobox);
                }
            });
            search.start();
        }
    }//GEN-LAST:event_lblPreMouseClicked

    private void lblFPageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFPageMouseClicked
        // TODO add your handling code here:
        if (pageIndex > 1) {
            pageIndex = 1;
            search = new Thread(new Runnable() {
                @Override
                public void run() {
                    paging(usingCombobox);
                }
            });
            search.start();
        }
    }//GEN-LAST:event_lblFPageMouseClicked

    private void lblNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNextMouseClicked
        // TODO add your handling code here:
        if (pageIndex < 10) {
            pageIndex++;
            search = new Thread(new Runnable() {
                @Override
                public void run() {
                    paging(usingCombobox);
                }
            });
            search.start();
        }
    }//GEN-LAST:event_lblNextMouseClicked

    private void lblLPageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLPageMouseClicked
        // TODO add your handling code here:
        if (pageIndex < 10) {
            pageIndex = 10;
            search = new Thread(new Runnable() {
                @Override
                public void run() {
                    paging(usingCombobox);
                }
            });
        }
    }//GEN-LAST:event_lblLPageMouseClicked

    private void miExistedPlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExistedPlActionPerformed
        // TODO add your handling code here:
        String path = playlistPath;

        JFileChooser chooser = new JFileChooser(playlistPath);
        chooser.setFileFilter(new FileNameExtensionFilter("", "pl"));
        int option = chooser.showOpenDialog(null);
        if (option == JFileChooser.FILES_ONLY) {
            String playlistName = chooser.getName(chooser.getSelectedFile());
            path += "\\" + playlistName;
        } else {
            return;
        }
        Vector<VideoInfo> oldResult = xmlDecoder(path);
        playlist = new Vector<>();
        if (oldResult != null) {
            for (VideoInfo r : oldResult) {
                playlist.add(r);
            }
        }

        VideoInfo result = new VideoInfo();
        result.setIdResult(videoResults.get(resultList.getSelectedIndex()).getIdResult());
        result.setImgUrl(videoResults.get(resultList.getSelectedIndex()).getImgUrl());
        result.setTime(videoResults.get(resultList.getSelectedIndex()).getTime());
        result.setTitle(videoResults.get(resultList.getSelectedIndex()).getTitle());
        result.setUploadUser(videoResults.get(resultList.getSelectedIndex()).getUploadUser());
        result.setViewCount(videoResults.get(resultList.getSelectedIndex()).getViewCount());

        playlist.add(result);

        try {
            xmlEncoder(path, playlist);
            JOptionPane.showMessageDialog(this, "Add successful!");
        } catch (Exception ex) {
        }

    }//GEN-LAST:event_miExistedPlActionPerformed

    private void miNewPlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNewPlActionPerformed
        // TODO add your handling code here:
        String playlistName = JOptionPane.showInputDialog("Playlist name:");
        if (playlistName == null) {
            return;
        }
        String path = playlistPath + "\\" + playlistName;
        while (new File(path).isFile()) {
            JOptionPane.showMessageDialog(this, "This name already exists! Try another name.");
            playlistName = JOptionPane.showInputDialog("Playlist name:");
            if (playlistName == null) {
                return;
            }
            path = playlistPath + "\\" + playlistName;
        }
        playlist = new Vector<>();
        VideoInfo result = new VideoInfo();
        result.setIdResult(videoResults.get(resultList.getSelectedIndex()).getIdResult());
        result.setImgUrl(videoResults.get(resultList.getSelectedIndex()).getImgUrl());
        result.setTime(videoResults.get(resultList.getSelectedIndex()).getTime());
        result.setTitle(videoResults.get(resultList.getSelectedIndex()).getTitle());
        result.setUploadUser(videoResults.get(resultList.getSelectedIndex()).getUploadUser());
        result.setViewCount(videoResults.get(resultList.getSelectedIndex()).getViewCount());

        playlist.add(result);
        xmlEncoder(path, playlist);
        JOptionPane.showMessageDialog(this, "Add successful!");

    }//GEN-LAST:event_miNewPlActionPerformed

    private void miAddAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAddAllActionPerformed
        JOptionPane option = new JOptionPane();
        String playlistName = option.showInputDialog("Playlist name:");
        if (playlistName != null) {
            String path = playlistPath + "\\" + playlistName;
            while (new File(path).isFile()) {
                JOptionPane.showMessageDialog(this, "This name already exists! Try another name.");
                path = playlistPath + "\\" + JOptionPane.showInputDialog("Playlist name:");
            }
            try {
                playlist = videoResults;
                if (playlist != null) {
                    xmlEncoder(path, playlist);
                    JOptionPane.showMessageDialog(this, "Add successful!");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_miAddAllActionPerformed

    private void miViewPlaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miViewPlaylistActionPerformed
        // TODO add your handling code here:
        if (vpl != null && vpl.isVisible()) {
        } else {
            vpl = new ViewPlaylist(this);
            vpl.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            vpl.setLocation(400, 300);
            vpl.setVisible(true);
        }


    }//GEN-LAST:event_miViewPlaylistActionPerformed

    private void lbVolumeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbVolumeMouseClicked
        // TODO add your handling code here:
        if (embededMPC != null) {
            countVol++;
            if (countVol % 2 == 0) {
                embededMPC.getMediaPlayer().mute(true);
                lbVolume.setIcon(new ImageIcon(getClass().getResource("/ToolIcon/audio_mute.png")));
                currentVol = sliderVol.getValue();
                sliderVol.setValue(0);

            } else {
                embededMPC.getMediaPlayer().mute(false);
                lbVolume.setIcon(iconVol);
                sliderVol.setValue(currentVol);
            }
        }
    }//GEN-LAST:event_lbVolumeMouseClicked

    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        // TODO add your handling code here:
        if (btnOrder.isSelected()) {
            btnOrder.setToolTipText("Turn shuffle off");
            miShuffle.setSelected(true);
        } else {
            btnOrder.setToolTipText("Turn shuffle on");
            miShuffle.setSelected(false);
        }
    }//GEN-LAST:event_btnOrderActionPerformed

    private void btnRepeatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepeatActionPerformed
        // TODO add your handling code here:
        if (btnRepeat.isSelected()) {
            btnRepeat.setToolTipText("Turn repeat off");
            miRepeat.setSelected(true);
        } else {
            btnRepeat.setToolTipText("Turn repeat on");
            miRepeat.setSelected(false);
        }
    }//GEN-LAST:event_btnRepeatActionPerformed

    private void miStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miStopActionPerformed
        // TODO add your handling code here:
        stopVideo();
    }//GEN-LAST:event_miStopActionPerformed

    private void miPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPrevActionPerformed
        // TODO add your handling code here:
        playPrevVideo();
    }//GEN-LAST:event_miPrevActionPerformed

    private void miPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPPActionPerformed
        // TODO add your handling code here:
        doPlayPauseVideo();
    }//GEN-LAST:event_miPPActionPerformed

    private void miNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNextActionPerformed
        // TODO add your handling code here:
        playNextVideo();
    }//GEN-LAST:event_miNextActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        System.out.println(evt.getKeyChar());
    }//GEN-LAST:event_formKeyPressed

    private void pnResultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnResultMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnResultMouseClicked

    private void rdbEngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbEngActionPerformed
        // TODO add your handling code here:
        lang = "en";
        showSubtitle(lang);
    }//GEN-LAST:event_rdbEngActionPerformed

    private void rdbMIVietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbMIVietActionPerformed
        // TODO add your handling code here:
        lang = "vi";
        showSubtitle(lang);
    }//GEN-LAST:event_rdbMIVietActionPerformed

    private void rdbKoreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbKoreaActionPerformed
        // TODO add your handling code here:
        lang = "kr";
        showSubtitle(lang);
    }//GEN-LAST:event_rdbKoreaActionPerformed

    private void rdbGermanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbGermanActionPerformed
        // TODO add your handling code here:
        lang = "de";
        showSubtitle(lang);
    }//GEN-LAST:event_rdbGermanActionPerformed

    private void rdbRussianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbRussianActionPerformed
        // TODO add your handling code here:
        lang = "ru";
        showSubtitle(lang);
    }//GEN-LAST:event_rdbRussianActionPerformed

    private void rdbOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbOffActionPerformed
        // TODO add your handling code here:
        lang = "abc";
        showSubtitle(lang);
        File file = new File(subPath);
        if (file.exists()) {
            embededMPC.getMediaPlayer().setSubTitleFile(new File(""));
            file.delete();
        }

    }//GEN-LAST:event_rdbOffActionPerformed

    private void cbbFilter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbFilter2ActionPerformed
        // TODO add your handling code here:
        secondarySearching();
    }//GEN-LAST:event_cbbFilter2ActionPerformed

    private void cbmiAlwaysOnTopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbmiAlwaysOnTopActionPerformed
        // TODO add your handling code here:
        Desktube.this.setAlwaysOnTop(cbmiAlwaysOnTop.getState());
    }//GEN-LAST:event_cbmiAlwaysOnTopActionPerformed

    private void miShowThisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miShowThisActionPerformed
        // TODO add your handling code here:
        int index = resultList.getSelectedIndex();
        playVideoFromSearchPlaylist(index);
    }//GEN-LAST:event_miShowThisActionPerformed

    private void miSaveThisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSaveThisActionPerformed
        // TODO add your handling code here:
        String pID = playlistResults.get(resultList.getSelectedIndex()).getPlaylisID();
        int index1 = 0;
        int index2 = 0;
        int index3 = 0;
        int index4 = 0;
        try {
            httpclient = new DefaultHttpClient();
            httpget = new HttpGet("http://www.youtube.com/playlist?list=" + pID);
            responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpget, responseHandler);
            String startStr = "<div class=\"thumb-container\">";
            Vector<VideoInfo> videoInfos = new Vector<>();

            //<editor-fold defaultstate="collapsed" desc="load data to Video Result">
            while (true) {
                index1 = responseBody.indexOf(startStr, index1 + 1);
                index2 = responseBody.indexOf("<p class=\"video-description yt-ui-ellipsis yt-ui-ellipsis-2\">", index1 + startStr.length());
                if (index1 >= 0 && index2 > index1) {
                    String temp = responseBody.substring(index1 + startStr.length(), index2);
                    String startStrVID = "<a href=\"/watch?v=";
                    String startStrTitle = "<span class=\"title video-title\" dir=\"ltr\">";
                    String startStrTime = "<span class=\"video-time\">";
                    String startStrVCount = "<span class=\"video-view-count\">";
                    String startStrUploader = "dir=\"ltr\">";
                    String vid = "", title = "", time = "", vcount = "", uploader = "", img = "";



                    index3 = temp.indexOf(startStrVID, 0);
                    index4 = temp.indexOf("&amp;", index3 + startStrVID.length());
                    if (index3 >= 0 && index4 > index3) {
                        vid = temp.substring(index3 + startStrVID.length(), index4);
                    }

                    index3 = temp.indexOf(startStrTitle, 0);
                    index4 = temp.indexOf("</span>", index3 + startStrTitle.length());
                    if (index3 >= 0 && index4 > index3) {
                        title = temp.substring(index3 + startStrTitle.length(), index4);
                    }

                    index3 = temp.indexOf(startStrTime, 0);
                    index4 = temp.indexOf("</span>", index3 + startStrTime.length());
                    if (index3 >= 0 && index4 > index3) {
                        time = temp.substring(index3 + startStrTime.length(), index4);
                    }

                    index3 = temp.indexOf(startStrVCount, 0);
                    index4 = temp.indexOf("</span>", index3 + startStrVCount.length());
                    if (index3 >= 0 && index4 > index3) {
                        vcount = temp.substring(index3 + startStrVCount.length(), index4).replaceAll("\n", "").replaceAll(" ", "");
                    }

                    index3 = temp.indexOf(startStrUploader, temp.indexOf(title, title.length()));
                    index4 = temp.indexOf("</a>", index3 + startStrUploader.length());
                    if (index3 >= 0 && index4 > index3) {
                        uploader = temp.substring(index3 + startStrUploader.length(), index4);
                    } else {
                        break;
                    }

                    img = "http://i1.ytimg.com/vi/" + vid + "/mqdefault.jpg";
                    VideoInfo info = new VideoInfo(vid, title, time, uploader, img, vcount);
                    videoInfos.add(info);
                } else {
                    break;
                }
            }
            //</editor-fold>

            String playlistName = JOptionPane.showInputDialog("Playlist name:");
            if (playlistName == null) {
                return;
            }
            String path = playlistPath + "\\" + playlistName;
            while (new File(path).isFile()) {
                JOptionPane.showMessageDialog(this, "This name already exists! Try another name.");
                playlistName = JOptionPane.showInputDialog("Playlist name:");
                if (playlistName == null) {
                    return;
                }
                path = playlistPath + "\\" + playlistName;
            }
            xmlEncoder(path, videoInfos);
            JOptionPane.showMessageDialog(this, "Add successful!");

        } catch (IOException ex) {
            Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }
        }
    }//GEN-LAST:event_miSaveThisActionPerformed

    private void miHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miHomeActionPerformed
        // TODO add your handling code here:
        if(embededMPC != null && embededMPC.getMediaPlayer().isPlaying())
            embededMPC.getMediaPlayer().stop();
        embededMPC = null;
        LblTitleVideo.setText("");
        hidePageIndex();
        pnResult.removeAll();
        reinitDefaultPanel();
        pnResult.setBackground(new Color(221,228,235));
        pnTitle.setBackground(Color.BLACK);
        lblTitle.setText("Desktube");
        sliderSeek.setValue(0);
        sliderVol.setValue(100);
        lblHour.setVisible(false);
        lblMinute.setText("00");
        lblSecond.setText("00");
        lblTotalTime.setText("00 : 00");
        setPlayerType(false);
    }//GEN-LAST:event_miHomeActionPerformed

    private void cbbFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbFilterActionPerformed
        // TODO add your handling code here:
        searching();
    }//GEN-LAST:event_cbbFilterActionPerformed

    private void miShuffleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miShuffleActionPerformed
        // TODO add your handling code here:
        if (miShowThis.isSelected()) {
            btnOrder.setToolTipText("Turn shuffle off");
            btnOrder.setSelected(true);
        } else {
            btnOrder.setToolTipText("Turn shuffle on");
            btnOrder.setSelected(false);
        }
    }//GEN-LAST:event_miShuffleActionPerformed

    private void miRepeatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miRepeatActionPerformed
        // TODO add your handling code here:
        if (miRepeat.isSelected()) {
            btnRepeat.setToolTipText("Turn repeat off");
            btnRepeat.setSelected(true);
        } else {
            btnRepeat.setToolTipText("Turn repeat on");
            btnRepeat.setSelected(false);
        }
    }//GEN-LAST:event_miRepeatActionPerformed

    private void btnFullSMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFullSMouseEntered
        // TODO add your handling code here:
        setButtonBorder(btnFullS, true);
    }//GEN-LAST:event_btnFullSMouseEntered

    private void btnFullSMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFullSMouseExited
        // TODO add your handling code here:
        setButtonBorder(btnFullS, false);
    }//GEN-LAST:event_btnFullSMouseExited

    private void btnStopMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStopMouseEntered
        // TODO add your handling code here:
        setButtonBorder(btnStop, true);
    }//GEN-LAST:event_btnStopMouseEntered

    private void btnStopMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStopMouseExited
        // TODO add your handling code here:
        setButtonBorder(btnStop, false);
    }//GEN-LAST:event_btnStopMouseExited

    private void btnPreviousMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPreviousMouseEntered
        // TODO add your handling code here:
        setButtonBorder(btnPrevious, true);
    }//GEN-LAST:event_btnPreviousMouseEntered

    private void btnPreviousMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPreviousMouseExited
        // TODO add your handling code here:
        setButtonBorder(btnPrevious, false);
    }//GEN-LAST:event_btnPreviousMouseExited

    private void btnPlayPauseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPlayPauseMouseEntered
        // TODO add your handling code here:
        setButtonBorder(btnPlayPause, true);
    }//GEN-LAST:event_btnPlayPauseMouseEntered

    private void btnPlayPauseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPlayPauseMouseExited
        // TODO add your handling code here:
        setButtonBorder(btnPlayPause, false);
    }//GEN-LAST:event_btnPlayPauseMouseExited

    private void btnNextMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseEntered
        // TODO add your handling code here:
        setButtonBorder(btnNext, true);
    }//GEN-LAST:event_btnNextMouseEntered

    private void btnNextMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseExited
        // TODO add your handling code here:
        setButtonBorder(btnNext, false);
    }//GEN-LAST:event_btnNextMouseExited

    private void btnListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnListMouseEntered
        // TODO add your handling code here:
        setButtonBorder(btnList, true);
    }//GEN-LAST:event_btnListMouseEntered

    private void btnListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnListMouseExited
        // TODO add your handling code here:
        setButtonBorder(btnList, false);
    }//GEN-LAST:event_btnListMouseExited

    private void lblBackPageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBackPageMouseClicked
        // TODO add your handling code here:
        lblBackPage.setVisible(false);
        setPlayerType(false);
        if(embededMPC.getMediaPlayer().isPlaying()) embededMPC.getMediaPlayer().stop();
        lblTotalTime.setText("00:00");
        if(SwingUtilities.isLeftMouseButton(evt)){
            pnBrowser = new JPanel(new BorderLayout());
            pnBrowser.add(scrChannel, BorderLayout.CENTER);
            pnResult.removeAll();
            pnResult.add(pnBrowser, BorderLayout.CENTER);
            resultList.setListData(new Object[0]);
            pnResult.revalidate();
        }
    }//GEN-LAST:event_lblBackPageMouseClicked
    public void setButtonBorder(JButton btn, boolean value) {
        btn.setContentAreaFilled(value);
    }

    public void showSubtitle(String lang) {
        final String final_lang = lang;
        if (lang != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (video_id != null) {
                        try {
                            String sub_url = "http://www.video.google.com/timedtext?lang=" + final_lang + "&v=" + video_id;
                            httpclient = new DefaultHttpClient();
                            httpget = new HttpGet(sub_url);
                            responseHandler = new BasicResponseHandler();
                            String xml_sub = httpclient.execute(httpget, responseHandler);

                            String subtitle = convertXmlToSrt(xml_sub);
                            if (subtitle != null) {
                                if (embededMPC != null) {

                                    PrintWriter writer = new PrintWriter(subPath, "UTF8");
                                    writer.write(subtitle);
                                    writer.close();
                                    embededMPC.getMediaPlayer().setSubTitleFile(new File(subPath));
                                }
                            } else {
                                if (embededMPC != null) {
                                    File f = new File(subPath);
                                    f.delete();
                                    PrintWriter writer = new PrintWriter(subPath, "UTF8");
                                    writer.write("");
                                    embededMPC.getMediaPlayer().setSubTitleFile(new File(subPath));
                                }
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }).start();
        } else {
            
        }
    }

    private synchronized void loadSearchResultToDataPanels(String text, int page, String filter) throws IOException, URISyntaxException {

        URI uri = new URI(null, null, text, null);
        String query_ = uri.toString();
        String imgUrl;
        try {
            httpclient = new DefaultHttpClient();
            httpget = new HttpGet("http://www.youtube.com/results?search_query=" + query_ + "&filters=" + filter + "&lclk=" + filter + "&page=" + page);
            responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpget, responseHandler);
            String startStr = "<li class=\"yt-lockup2 clearfix yt-uix-tile result-item-padding has-hover-effects";
            int index1 = 0;
            int index2 = 0;
            Vector<String> infoList = new Vector();
            switch (filter) {
                case "Video":
                    //<editor-fold defaultstate="collapsed" desc="search-video">
                    videoResults = new Vector<>();
                    while (true) {

                        index1 = responseBody.indexOf(startStr, index1 + 1);
                        if (index1 < 0) {
                            break;
                        }
                        index2 = responseBody.indexOf(">", index1 + startStr.length());
                        if (index2 > 0) {
                            String temp = responseBody.substring(index1 + startStr.length(), index2);
                            infoList.add(temp);
                        } else {
                            break;
                        }
                    }
                    for (int i = 0; i < infoList.size(); i++) {
                        String result = infoList.get(i).toString();
                        String startStrId = "data-context-item-id=\"";
                        String startStrTitle = "data-context-item-title=\"";
                        String startStrTime = "data-context-item-time=\"";
                        String startStrUser = "data-context-item-user=\"";
                        String startStrView = "data-context-item-views=\"";
                        String id = "";
                        String title = "";
                        String time = "";
                        String user = "";
                        String view = "";
                        int check = 0;
                        index1 = result.indexOf(startStrView, index1 + 1);
                        if (index1 < 0) {
                            if (i == infoList.size() - 1) {
                                break;
                            }
                        } else {
                            index2 = result.indexOf("\"", index1 + startStrView.length());
                            if (index2 > 0) {
                                view = result.substring(index1 + startStrView.length(), index2);
                                index1 = 0;
                                check++;
                            } else {
                                break;
                            }
                        }

                        index1 = result.indexOf(startStrTitle, index1 + 1);
                        if (index1 < 0) {
                            if (i == infoList.size() - 1) {
                                break;
                            }
                        } else {
                            index2 = result.indexOf("\"", index1 + startStrTitle.length());
                            if (index2 > 0) {
                                title = result.substring(index1 + startStrTitle.length(), index2);
                                index1 = 0;
                                check++;
                            } else {
                                break;
                            }
                        }

                        index1 = result.indexOf(startStrUser, index1 + 1);
                        if (index1 < 0) {
                            if (i == infoList.size() - 1) {
                                break;
                            }
                        } else {
                            index2 = result.indexOf("\"", index1 + startStrUser.length());
                            if (index2 > 0) {
                                user = result.substring(index1 + startStrUser.length(), index2);
                                index1 = 0;
                                check++;
                            } else {
                                break;
                            }
                        }

                        index1 = result.indexOf(startStrTime, index1 + 1);
                        if (index1 < 0) {
                            if (i == infoList.size() - 1) {
                                break;
                            }
                        } else {
                            index2 = result.indexOf("\"", index1 + startStrTime.length());
                            if (index2 > 0) {
                                time = result.substring(index1 + startStrTime.length(), index2);
                                index1 = 0;
                                check++;
                            } else {
                                break;
                            }
                        }

                        index1 = result.indexOf(startStrId, index1 + 1);
                        if (index1 < 0) {
                            if (i == infoList.size() - 1) {
                                break;
                            }
                        } else {
                            index2 = result.indexOf("\"", index1 + startStrId.length());
                            if (index2 > 0) {
                                id = result.substring(index1 + startStrId.length(), index2);
                                index1 = 0;
                                check++;
                            } else {
                                break;
                            }
                        }

                        if (check == 5) {
                            imgUrl = "http://i1.ytimg.com/vi/" + id + "/mqdefault.jpg";
                            VideoInfo result_temp = new VideoInfo(id, title, time, user, imgUrl, view);
                            videoResults.add(result_temp);
                        }
                    }

                    //</editor-fold>
                    break;
                case "Channel":
                    //<editor-fold defaultstate="collapsed" desc="search channel">
                    channelResults = new Vector<>();
                    while (true) {
                        index1 = responseBody.indexOf(startStr, index1 + 1);
                        if (index1 < 0) {
                            break;
                        }
                        index2 = responseBody.indexOf("</div>\n"
                                + "    \n"
                                + "  </li>", index1 + startStr.length());
                        if (index2 > 0) {
                            String temp = responseBody.substring(index1 + startStr.length(), index2);
                            infoList.add(temp);
                        } else {
                            break;
                        }
                    }
                    for (int i = 0; i < infoList.size(); i++) {
                        String result = infoList.get(i).toString();
                        String startStrChannelID = "data-channel-external-id=\"";
                        String startStrImgThumbnail = "<span class=\"yt-thumb-clip-inner\">";
                        String startStrImgURL = "data-thumb=\"";
                        String startTitle = "dir=\"ltr\"title=\"";
                        String startStrTimeActive = "</a></li><li>";
                        String startStrVideos = "</li><li>";
                        String startStrSubcriber = "<span class=\"yt-subscription-button-subscriber-count-unbranded-horizontal\" >";
                        String startStrURI = "<a href=\"";
                        String channelID = "";
                        String imgThumbnail = "";
                        String imgThumbnailURL = "";
                        String title = "";
                        String timeActive = "";
                        String videos = "";
                        String subcriber = "";
                        String href = "";
                        index1 = result.indexOf(startStrImgThumbnail, index1 + 1);
                        int check = 0;
                        if (index1 < 0) {
                            if (i == infoList.size() - 1) {
                                break;
                            }
                        } else {
                            index2 = result.indexOf("<span class=\"vertical-align\">", index1 + startStrImgThumbnail.length());
                            if (index2 > 0) {
                                imgThumbnail = result.substring(index1 + imgThumbnail.length(), index2);
                                index1 = imgThumbnail.indexOf(startStrImgURL, 0);
                                if (index1 < 0) {
                                    startStrImgURL = "src=\"";
                                    index1 = imgThumbnail.indexOf(startStrImgURL, 0);
                                }
                                imgThumbnailURL = imgThumbnail.substring(index1 + startStrImgURL.length(), imgThumbnail.indexOf("\"", index1 + startStrImgURL.length()));
                                while (imgThumbnailURL.charAt(0) == '/') {
                                    imgThumbnailURL = imgThumbnailURL.substring(1, imgThumbnailURL.length());
                                }
                                index1 = 0;
                                check++;
                            } else {
                                break;
                            }
                        }

                        index1 = result.indexOf(startStrChannelID, index1 + 1);
                        if (index1 < 0) {
                            if (i == infoList.size() - 1) {
                                break;
                            }
                        } else {
                            index2 = result.indexOf("\"", index1 + startStrChannelID.length());
                            if (index2 > 0) {
                                channelID = result.substring(index1 + startStrChannelID.length(), index2);
                                index1 = 0;
                                check++;
                            } else {
                                break;
                            }
                        }

                        index1 = result.indexOf(startTitle, index1 + 1);
                        if (index1 < 0) {
                            if (i == infoList.size() - 1) {
                                break;
                            }
                        } else {
                            index2 = result.indexOf("\"", index1 + startTitle.length());
                            if (index2 > 0) {
                                title = result.substring(index1 + startTitle.length(), index2);
                                index1 = 0;
                                check++;
                            } else {
                                break;
                            }
                        }

                        index1 = result.indexOf(startStrTimeActive, index1 + 1);
                        if (index1 < 0) {
                            if (i == infoList.size() - 1) {
                                break;
                            }
                        } else {
                            index2 = result.indexOf("</li>", index1 + startStrTimeActive.length());
                            if (index2 > 0) {
                                timeActive = result.substring(index1 + startStrTimeActive.length(), index2);
                                index1 = 0;
                                check++;
                            } else {
                                break;
                            }
                        }

                        index1 = result.indexOf(startStrVideos, result.indexOf(timeActive) + timeActive.length());
                        if (index1 < 0) {
                            if (i == infoList.size() - 1) {
                                break;
                            }
                        } else {
                            index2 = result.indexOf("</li>", index1 + startStrVideos.length());
                            if (index2 > 0) {
                                videos = result.substring(index1 + startStrVideos.length(), index2);
                                index1 = 0;
                                check++;
                            } else {
                                break;
                            }
                        }

                        index1 = result.indexOf(startStrSubcriber, index1 + 1);
                        if (index1 < 0) {
                            if (i == infoList.size() - 1) {
                                break;
                            }
                        } else {
                            index2 = result.indexOf("</span>", index1 + startStrSubcriber.length());
                            if (index2 > 0) {
                                subcriber = result.substring(index1 + startStrSubcriber.length(), index2);
                                index1 = 0;
                                check++;
                            } else {
                                break;
                            }
                        }

                        index1 = result.indexOf(startStrURI, index1 + 1);
                        if (index1 < 0) {
                            if (i == infoList.size() - 1) {
                                break;
                            }
                        } else {
                            index2 = result.indexOf("\"", index1 + startStrURI.length());
                            if (index2 > 0) {
                                href = result.substring(index1 + startStrURI.length(), index2);
                                index1 = 0;
                                check++;
                            } else {
                                break;
                            }
                        }

                        if (check == 7) {
                            ChannelInfo channelInfo = new ChannelInfo(channelID, imgThumbnailURL, title, timeActive, videos, subcriber, href);
                            channelResults.add(channelInfo);
                        }
                    }

                    //</editor-fold>
                    break;
                case "Playlist":
                    //<editor-fold defaultstate="collapsed" desc="search-playlist">
                    startStr = "<li class=\"yt-lockup2 clearfix yt-uix-tile result-item-padding has-hover-effects yt-lockup2-playlist yt-lockup2-tile context-data-item\"";
                    String startStrPID = "data-context-item-id=\"";
                    String startStrUploader = "data-context-item-user=\"";
                    String startStrPTitle = "data-context-item-title=\"";
                    String startStrVideoCount = "data-context-item-count=\"";
                    String startStrVideo_id = "data-context-item-videos=\"[&quot;";
                    String pId, pTitle, video_id, uploader;
                    int video_count = 0;
                    playlistResults = new Vector<>();
                    while (true) {
                        index1 = responseBody.indexOf(startStr, index1 + 1);
                        index2 = responseBody.indexOf(">", index1 + startStr.length());
                        if (index1 >= 0 && index2 > index1) {
                            String temp = responseBody.substring(index1 + startStr.length(), index2);
                            infoList.add(temp);
                        } else {
                            break;
                        }
                    }
                    for (String temp : infoList) {
                        index1 = temp.indexOf(startStrPID, 0);
                        if (index1 >= 0) {
                            index2 = temp.indexOf("\"", index1 + startStrPID.length());
                            pId = temp.substring(index1 + startStrPID.length(), index2);
                        } else {
                            break;
                        }

                        index1 = temp.indexOf(startStrUploader, 0);
                        if (index1 >= 0) {
                            index2 = temp.indexOf("\"", index1 + startStrUploader.length());
                            uploader = temp.substring(index1 + startStrUploader.length(), index2);
                        } else {
                            break;
                        }

                        index1 = temp.indexOf(startStrPTitle, 0);
                        if (index1 >= 0) {
                            index2 = temp.indexOf("\"", index1 + startStrPTitle.length());
                            pTitle = temp.substring(index1 + startStrPTitle.length(), index2);
                        } else {
                            break;
                        }

                        index1 = temp.indexOf(startStrVideoCount, 0);
                        if (index1 >= 0) {
                            index2 = temp.indexOf("\"", index1 + startStrVideoCount.length());
                            video_count = Integer.parseInt(temp.substring(index1 + startStrVideoCount.length(), index2));
                        } else {
                            break;
                        }

                        index1 = temp.indexOf(startStrVideo_id, 0);
                        if (index1 >= 0) {
                            index2 = temp.indexOf("&quot;", index1 + startStrVideo_id.length());
                            video_id = temp.substring(index1 + startStrVideo_id.length(), index2);
                        } else {
                            break;
                        }

                        String image_url = "http://i1.ytimg.com/vi/" + video_id + "/mqdefault.jpg";
                        PlaylistInfo info = new PlaylistInfo(pId, uploader, pTitle, video_count, image_url);
                        playlistResults.add(info);
                    }
                    //</editor-fold>
                    break;
            }


        } finally {
            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }
        }
    }

    public synchronized void playVideo(int index) {
        if (index >= 0) {
            showList();
            playing_video_index = index;
            String title = videoResults.get(index).getTitle();
            pnTitle.setBackground(new Color(221, 228, 235));
            LblTitleVideo.setText("<html>" + title + "</html>");
            lblTitle.setForeground(new Color(0, 0, 0));
            lblTitle.setText("<html>" + title + "</html>");
            video_id = videoResults.get(index).getIdResult();
            String video_url = getVideoURL(video_id);
            if (embededMPC != null) {
                embededMPC.getMediaPlayer().stop();
            }
            String TotalTime = "";

            for (VideoInfo result : videoResults) {
                if (result.getIdResult().equals(video_id)) {
                    TotalTime = result.getTime();
                    break;
                }
            }

            lblTotalTime.setText(TotalTime);
            embededMPC.getMediaPlayer().playMedia(video_url, "{\"tcp-caching=800\"}");
            System.out.println(video_url);
        }

    }

    public void loadPlayerToPanel() {
        if (embededMPC != null) {

            sliderSeek.setValue(0);
            embededMPC.getMediaPlayer().stop();
            embededMPC = null;
        }
        embededMPC = new EmbeddedMediaPlayerComponent();
        embededMPC.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventListener() {
            @Override
            public void mediaChanged(MediaPlayer mp, libvlc_media_t l, String string) {
                lblMinute.setText("00");
            }

            @Override
            public void opening(MediaPlayer mp) {
            }

            @Override
            public void buffering(MediaPlayer mp, float f) {
            }

            @Override
            public void playing(MediaPlayer mp) {
                long VIDEO_Length = mp.getMediaMeta().getLength();
                sliderSeek.setMaximum((int) VIDEO_Length);
                btnStop.setEnabled(true);
                btnPlayPause.setEnabled(true);
                mnPlayback.setEnabled(true);
                mAddVideo.setEnabled(true);
                btnPlayPause.setIcon(new ImageIcon(getClass().getResource("/ToolIcon/1369380612_pause.png")));
                if (resultList.getSelectedIndex() == 0) {
                    btnNext.setEnabled(true);
                    btnPrevious.setEnabled(false);
                }
                if (resultList.getSelectedIndex() > 0 && resultList.getSelectedIndex() < resultList.getLastVisibleIndex()) {
                    btnNext.setEnabled(true);
                    btnPrevious.setEnabled(true);
                }
                if (resultList.getSelectedIndex() == resultList.getLastVisibleIndex()) {
                    btnNext.setEnabled(false);
                    btnPrevious.setEnabled(true);
                }
                if (resultList.getSelectedIndex() == resultList.getModel().getSize() - 1) {
                    btnNext.setEnabled(false);
                    btnPrevious.setEnabled(false);
                }
            }

            @Override
            public void paused(MediaPlayer mp) {
            }

            @Override
            public void stopped(MediaPlayer mp) {
            }

            @Override
            public void forward(MediaPlayer mp) {
            }

            @Override
            public void backward(MediaPlayer mp) {
            }

            @Override
            public void finished(MediaPlayer mp) {
                embededMPC.getMediaPlayer().setSubTitleFile(new File(""));
                File file = new File(subPath);
                if (file.isFile()) {
                    file.delete();
                }
                sliderSeek.setValue(0);
                setTimer(0);
                int index = playing_video_index;
                if (!miStopAfter.isSelected()) {
                    if (btnOrder.isVisible() && btnRepeat.isVisible()) {
                        if (btnRepeat.isSelected()) {
                            embededMPC.getMediaPlayer().stop();
                            embededMPC.getMediaPlayer().setTime(0);
                            embededMPC.getMediaPlayer().play();
                        }
                        if (!btnOrder.isSelected()) {
                            playVideo(index + 1);
                            resultList.setSelectedIndex(index + 1);
                        } else {
                            int size = dataPanels.length;
                            Random ran = new Random();
                            int ran_index = ran.nextInt(size - 1);
                            playVideo(ran_index);
                            resultList.setSelectedIndex(ran_index);
                            pnResult.remove(scrollResultList);
                            scrollResultList = new JScrollPane(resultList);
                            scrollResultList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                            resultList.ensureIndexIsVisible(ran_index);
                            pnResult.setLayout(borderLayout);
                            pnResult.add(scrollResultList, borderLayout.EAST);
                        }
                    } else {
                        playVideo(playing_video_index);
                    }
                } else {
                    embededMPC.getMediaPlayer().stop();
                }
                if (miExitAfter.isSelected()) {
                    System.exit(0);
                }
            }

            @Override
            public void timeChanged(MediaPlayer mp, long l) {
                sliderSeek.setValue((int) l);
                int TimeShowed = (int) l / 1000;
                setTimer(TimeShowed);

            }

            @Override
            public void positionChanged(MediaPlayer mp, float f) {
            }

            @Override
            public void seekableChanged(MediaPlayer mp, int i) {
            }

            @Override
            public void pausableChanged(MediaPlayer mp, int i) {
            }

            @Override
            public void titleChanged(MediaPlayer mp, int i) {
            }

            @Override
            public void snapshotTaken(MediaPlayer mp, String string) {
            }

            @Override
            public void lengthChanged(MediaPlayer mp, long l) {
            }

            @Override
            public void videoOutput(MediaPlayer mp, int i) {
            }

            @Override
            public void error(MediaPlayer mp) {
            }

            @Override
            public void mediaMetaChanged(MediaPlayer mp, int i) {
            }

            @Override
            public void mediaSubItemAdded(MediaPlayer mp, libvlc_media_t l) {
            }

            @Override
            public void mediaDurationChanged(MediaPlayer mp, long l) {
            }

            @Override
            public void mediaParsedChanged(MediaPlayer mp, int i) {
            }

            @Override
            public void mediaFreed(MediaPlayer mp) {
            }

            @Override
            public void mediaStateChanged(MediaPlayer mp, int i) {
            }

            @Override
            public void newMedia(MediaPlayer mp) {
            }

            @Override
            public void subItemPlayed(MediaPlayer mp, int i) {
            }

            @Override
            public void subItemFinished(MediaPlayer mp, int i) {
            }

            @Override
            public void endOfSubItems(MediaPlayer mp) {
            }
        });
        pnResult.removeAll();
        pnBrowser = new JPanel(new BorderLayout());
        pnBrowser.add(embededMPC, BorderLayout.CENTER);
        pnResult.add(pnBrowser, BorderLayout.CENTER);
        showList();
//        pnResult.revalidate();


        btnPPCount = 1;
        btnPlayPause.setIcon(new ImageIcon(getClass().getResource("/ToolIcon/1369380612_pause.png")));

    }

    private synchronized String getVideoURL(String video_id) {
        String address = "https://www.youtube.com/watch?v=" + video_id;
        String decodeURL = "";
        String sig = "";
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(new URI(address));
            ResponseHandler<String> responseHandler_ = new BasicResponseHandler();
            String responseBody = httpClient.execute(httpGet, responseHandler_);
            String DataBlockStart = "\"url_encoded_fmt_stream_map\": \"";
            int index1 = 0;
            int index2 = 0;
            index1 = responseBody.indexOf(DataBlockStart, index1 + 1);
            index2 = responseBody.indexOf("\"", index1 + DataBlockStart.length());
            String subScript = responseBody.substring(index1 + DataBlockStart.length(), index2);
            index1 = 0;
            index2 = 0;
            String urlStart = "url=";
            String sigStart = "sig=";

            String url = "";
            index1 = subScript.indexOf(urlStart, index1);
            index2 = subScript.indexOf("\\u0026", index1 + urlStart.length());
            if (index1 >= 0) {
                if (index2 > 0) {
                    url = subScript.substring(index1 + urlStart.length(), index2);
                }
                if (url.charAt(url.length() - 1) == '%') {
                    url = url.substring(0, url.length() - 1);
                }
                int ErrorTextInUrl = url.indexOf(",", 0);
                if (ErrorTextInUrl > 0) {
                    String errorText = url.substring(ErrorTextInUrl, url.length());
                    url = url.replaceAll(errorText, "");
                }
                decodeURL = URLDecoder.decode(URLDecoder.decode(url, "UTF-8"), "UTF-8");
                index1 = 0;
                index2 = 0;

                index1 = subScript.indexOf(sigStart, index1);
                index2 = subScript.indexOf("\\u0026", index1 + urlStart.length());
                sig = subScript.substring(index1 + sigStart.length(), index2);

                if (sig == "") {
                    index2 = subScript.indexOf(",sig", index1 + urlStart.length());
                    sig = subScript.substring(index1 + sigStart.length(), index2);

                }
                if (sig == "") {
                    index2 = subScript.indexOf(",", index1 + urlStart.length());
                    sig = subScript.substring(index1 + sigStart.length(), index2);
                }
                if (sig == "") {
                    sigStart = "\\0026s=";
                    index1 = 0;
                    index1 = subScript.indexOf(sigStart, index1);
                    index2 = subScript.indexOf("\\u0026", index1 + sigStart.length());
                    sig = subScript.substring(index1 + sigStart.length(), index2);
                }
                if (sig.length() > 81) {
                    sig = sig.substring(0, 81);
                }
            } else {
            }

            String URLSignature = decodeURL + "&signature=" + sig;
            System.out.println(URLSignature);
            return URLSignature;
        } catch (URISyntaxException ex) {
            Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }
        }

        return null;
    }

    public void loadSearchVideoToList(int index) {
        if (scrollResultList != null) {
            pnResult.remove(scrollResultList);
        }
        if (dataPanels != null && dataPanels.length > 0) {
            btnList.setEnabled(true);
            renderer = new HoverListCellRenderer();
            resultList = new JList(dataPanels);
            resultList.setSelectedIndex(index);
            resultList.setCellRenderer(renderer);
            resultList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            resultList.setFixedCellWidth(350);
            resultList.setVisibleRowCount(200);
            resultList.addMouseListener(renderer.getHandler(resultList));
            resultList.addMouseMotionListener(renderer.getHandler(resultList));
            resultList.setCursor(new Cursor(Cursor.HAND_CURSOR));

            scrollResultList = new JScrollPane(resultList);
            scrollResultList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            resultList.ensureIndexIsVisible(resultList.getSelectedIndex());

            pnResult.setLayout(borderLayout);
            pnResult.add(scrollResultList, BorderLayout.EAST);

            resultList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    final MouseEvent final_evt = evt;
                    if (SwingUtilities.isLeftMouseButton(evt) && final_evt.getClickCount() == 1) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mAddVideo.setEnabled(true);
                                sliderSeek.setValue(0);
                                btnPlayPause.setIcon(new ImageIcon(getClass().getResource("/ToolIcon/1369380612_pause.png")));
                                int index = resultList.locationToIndex(final_evt.getPoint());
                                playVideo(index);
                                if (index == resultList.getFirstVisibleIndex()) {
                                    btnPrevious.setEnabled(false);
                                    btnNext.setEnabled(true);
                                } else if (index == resultList.getLastVisibleIndex()) {
                                    btnPrevious.setEnabled(true);
                                    btnNext.setEnabled(false);
                                } else {
                                    btnPrevious.setEnabled(true);
                                    btnNext.setEnabled(true);
                                }
                            }
                        }).start();
                    }
                }
            });



        } else {
        }

    }

    private void reinitDefaultPanel() {
        javax.swing.GroupLayout pnResultLayout = new javax.swing.GroupLayout(pnResult);
        pnResult.setLayout(pnResultLayout);
        pnResultLayout.setHorizontalGroup(
                pnResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnResultLayout.createSequentialGroup()
                .addContainerGap(442, Short.MAX_VALUE)
                .addComponent(pnDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(442, Short.MAX_VALUE)));
        pnResultLayout.setVerticalGroup(
                pnResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnResultLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(pnDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(172, Short.MAX_VALUE)));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LblTitleVideo;
    private javax.swing.JButton btnFullS;
    private javax.swing.JButton btnList;
    private javax.swing.JButton btnNext;
    private javax.swing.JToggleButton btnOrder;
    private javax.swing.JButton btnPlayPause;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JToggleButton btnRepeat;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnStop;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbbFilter;
    private javax.swing.JComboBox cbbFilter2;
    private javax.swing.JCheckBoxMenuItem cbmiAlwaysOnTop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenu jmnPlaylist;
    private javax.swing.JLabel lbPage;
    private javax.swing.JLabel lbVolume;
    private javax.swing.JLabel lblBackPage;
    private javax.swing.JLabel lblDBDot;
    private javax.swing.JLabel lblFPage;
    private javax.swing.JLabel lblHour;
    private javax.swing.JLabel lblLPage;
    private javax.swing.JLabel lblMinute;
    private javax.swing.JLabel lblNext;
    private javax.swing.JLabel lblPre;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblSearchDef;
    private javax.swing.JLabel lblSecond;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotalTime;
    private javax.swing.JLabel lblVolPercent;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JLabel logo;
    private javax.swing.JMenu mAddVideo;
    private javax.swing.JMenu mSubLang;
    private javax.swing.JMenuItem miAddAll;
    private javax.swing.JMenuItem miExistedPl;
    private javax.swing.JCheckBoxMenuItem miExitAfter;
    private javax.swing.JMenuItem miHome;
    private javax.swing.JMenuItem miNewPl;
    private javax.swing.JMenuItem miNext;
    private javax.swing.JMenuItem miPP;
    private javax.swing.JMenuItem miPrev;
    private javax.swing.JCheckBoxMenuItem miRepeat;
    private javax.swing.JMenuItem miSaveThis;
    private javax.swing.JMenuItem miShowThis;
    private javax.swing.JCheckBoxMenuItem miShuffle;
    private javax.swing.JMenuItem miStop;
    private javax.swing.JCheckBoxMenuItem miStopAfter;
    private javax.swing.JMenuItem miViewPlaylist;
    private javax.swing.JMenu mnFile;
    private javax.swing.JMenu mnPlayback;
    private javax.swing.JPanel pnContent;
    private javax.swing.JPanel pnControl;
    private javax.swing.JPanel pnDefault;
    private javax.swing.JPanel pnResult;
    private javax.swing.JPanel pnStatus;
    private javax.swing.JPanel pnTitle;
    private javax.swing.JPanel pnTop;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JRadioButtonMenuItem rdbEng;
    private javax.swing.JRadioButtonMenuItem rdbGerman;
    private javax.swing.JRadioButtonMenuItem rdbKorea;
    private javax.swing.JRadioButtonMenuItem rdbMIViet;
    private javax.swing.JRadioButtonMenuItem rdbOff;
    private javax.swing.JRadioButtonMenuItem rdbRussian;
    private javax.swing.JSlider sliderSeek;
    private javax.swing.JSlider sliderVol;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearchDef;
    // End of variables declaration//GEN-END:variables

    private void showPageIndex() {
        lbPage.setText("Page " + pageIndex);
        lblPre.setText("<");
        lblFPage.setText("<<");
        lblNext.setText(">");
        lblLPage.setText(">>");
        if (pageIndex == 1) {
            lblPre.setEnabled(false);
            lblFPage.setEnabled(false);
            lblNext.setEnabled(true);
            lblLPage.setEnabled(true);
        }
        if (pageIndex > 1) {
            lblPre.setEnabled(true);
            lblFPage.setEnabled(true);
            lblNext.setEnabled(true);
            lblLPage.setEnabled(true);
        }
        if (pageIndex == 10) {
            lblNext.setEnabled(false);
            lblLPage.setEnabled(false);
            lblPre.setEnabled(true);
            lblFPage.setEnabled(true);
        }
    }

    public void hidePageIndex() {
        lbPage.setText("");
        lblPre.setText("");
        lblFPage.setText("");
        lblNext.setText("");
        lblLPage.setText("");
    }

    private void showList() {
        if (scrollResultList != null) {
            pnResult.add(scrollResultList, BorderLayout.EAST);
            pnResult.revalidate();
        }
    }

    private void hideList() {
        if (scrollResultList != null) {
            pnResult.remove(scrollResultList);
            pnResult.revalidate();
        }
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

    private Vector<VideoInfo> xmlDecoder(String path) {
        Vector<VideoInfo> playlist = new Vector<>();
        XMLDecoder decoder;
        try {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
            try {
                playlist = (Vector<VideoInfo>) decoder.readObject();
            } catch (Exception e) {
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ViewPlaylist.class.getName()).log(Level.SEVERE, null, ex);
        }

        return playlist;
    }

    public void loadVideoDataToPanel() {
        if (videoResults != null) {

            dataPanels = new JPanel[videoResults.size()];
            JLabel image;
            JLabel lblInfo;
            String html;
            String infomation;
            JPanel panel;
            for (int i = 0; i < videoResults.size(); i++) {
                String title;
                if (videoResults.get(i).getTitle().length() > 150) {
                    title = videoResults.get(i).getTitle().substring(0, 150) + " ...";
                } else {
                    title = videoResults.get(i).getTitle();
                }
                image = new JLabel();
                lblInfo = new JLabel();
                html = "<html>"
                        + "	<img src=\"" + videoResults.get(i).getImgUrl() + "\"  height=\"85\" width=\"120\">"
                        + "</html>";
                image.setText(html);
                image.setPreferredSize(new Dimension(125, 85));
                infomation = "<html><b>&nbsp;" + title.toUpperCase() + "</b>"
                        + "<br/>&nbsp;&nbsp;&nbsp;Uploaded by: " + videoResults.get(i).getUploadUser()
                        + "<br/>&nbsp;&nbsp;&nbsp;Time: " + videoResults.get(i).getTime()
                        + "<br/>&nbsp;&nbsp;&nbsp;Views: " + videoResults.get(i).getViewCount()
                        + "</html>";

                lblInfo.setText(infomation);
                panel = new JPanel(new BorderLayout());
                panel.add(image, BorderLayout.EAST);
                panel.add(lblInfo, BorderLayout.CENTER);
                dataPanels[i] = panel;

            }
            if (dataPanels.length > 0) {
                miAddAll.setEnabled(true);
            }
        }
    }

    public JPanel[] getPanelsVideoInChannel(Vector<VideoInfo> videoInfos) {
        JPanel[] dataPanels = new JPanel[videoInfos.size()];
        JLabel image;
        JLabel lblInfo;
        String html;
        String infomation;
        JPanel panel;
        for (int i = 0; i < videoInfos.size(); i++) {
            String title;
            String img = videoInfos.get(i).getImgUrl();
            if (videoInfos.get(i).getTitle().length() > 30) {
                title = videoInfos.get(i).getTitle().substring(0, 30) + " ...";
            } else {
                title = videoInfos.get(i).getTitle();
            }
            image = new JLabel();
            lblInfo = new JLabel();
            html = "<html>"
                    + "	<img src=\"" + img + "\" height=\"120\" width=\"240\">"
                    + "</html>";
            image.setText(html);
            image.setPreferredSize(new Dimension(240, 120));
            infomation = "<html><b>&nbsp;" + title + "</b>"
                    + "<br/>&nbsp;&nbsp;&nbsp;Uploaded by: " + videoInfos.get(i).getUploadUser()
                    + "<br/>&nbsp;&nbsp;&nbsp;Time: " + videoInfos.get(i).getTime()
                    + "<br/>&nbsp;&nbsp;&nbsp;Views: " + videoInfos.get(i).getViewCount()
                    + "</html>";

            lblInfo.setText(infomation);

            panel = new JPanel(new BorderLayout());
            panel.add(image, BorderLayout.CENTER);
            panel.add(lblInfo, BorderLayout.SOUTH);
            panel.setToolTipText(videoInfos.get(i).getTitle());
            dataPanels[i] = panel;
        }
        return dataPanels;
    }

    public void loadChannelDataToPanel() {
        if (channelResults != null) {
            dataPanels = new JPanel[channelResults.size()];
            JLabel image;
            JLabel lblInfo;
            String html;
            String infomation;
            JPanel panel;
            for (int i = 0; i < channelResults.size(); i++) {
                String title;
                String img = channelResults.get(i).getImgThumbnail();
                if (img.indexOf("https://") < 0) {
                    img = "http://" + img;
                }
                if (channelResults.get(i).getTitle().length() > 150) {
                    title = channelResults.get(i).getTitle().substring(0, 150) + " ...";
                } else {
                    title = channelResults.get(i).getTitle();
                }
                image = new JLabel();
                lblInfo = new JLabel();
                html = "<html>"
                        + "	<img src=\"" + img + "\" height=\"100\" width=\"100\">"
                        + "</html>";
                image.setText(html);
                image.setPreferredSize(new Dimension(125, 85));
                infomation = "<html><b>&nbsp;" + title + "</b>"
                        + "<br/>&nbsp;&nbsp;&nbsp;Time Active: " + channelResults.get(i).getTimeActive()
                        + "<br/>&nbsp;&nbsp;&nbsp;Videos: " + channelResults.get(i).getVideos()
                        + "<br/>&nbsp;&nbsp;&nbsp;Subcribers: " + channelResults.get(i).getSubcriber()
                        + "</html>";

                lblInfo.setText(infomation);
                panel = new JPanel(new BorderLayout());
                panel.add(image, BorderLayout.EAST);
                panel.add(lblInfo, BorderLayout.CENTER);
                dataPanels[i] = panel;
            }
        }
    }

    public void setPlayerType(Boolean value) {
        btnOrder.setVisible(value);
        btnRepeat.setVisible(value);
        miShuffle.setEnabled(value);
        miRepeat.setEnabled(value);
    }

    public void setTimer(int time) {
        if (time < 3600) {
            lblDBDot.setText("");
            lblHour.setText("");
            if (time % 60 == 0) {
                lblMinute.setText("" + time / 60);
                lblSecond.setText("00");
            } else if (time % 60 > 1) {
                lblMinute.setText((time / 60 >= 10) ? "" + time / 60 : "0" + time / 60);
                lblSecond.setText((time % 60 >= 10) ? "" + time % 60 : "0" + time % 60);
            }
        } else {
            if (time % 3600 == 0) {
                lblDBDot.setText(":");
                lblHour.setText((time / 3600 >= 10) ? "" + time / 3600 : "0" + time / 3600);
                lblMinute.setText("00");
                lblSecond.setText("00");
            } else {
                lblDBDot.setText(":");
                lblHour.setText((time / 3600 >= 10) ? "" + time / 3600 : "0" + time / 3600);
                lblMinute.setText((((time % 3600) / 60) >= 10) ? "" + ((time % 3600) / 60) : "0" + ((time % 3600) / 60));
                lblSecond.setText(((time % 3600) % 60 >= 10) ? "" + (time % 3600) % 60 : "0" + (time % 3600) % 60);
            }
        }
    }

    public void stopVideo() {
        // TODO add your handling code here:
        if (embededMPC != null) {
            embededMPC.getMediaPlayer().stop();
            btnPPCount = 2;
            btnPlayPause.setIcon(new ImageIcon(getClass().getResource("/ToolIcon/1369380862_play.png")));
        }
    }

    public void playPrevVideo() {
        // TODO add your handling code here:
        countBtnListPerformed++;
        int index = resultList.getSelectedIndex() - 1;
        resultList.setSelectedIndex(index);
        playVideo(index);
    }

    public void doPlayPauseVideo() {
        // TODO add your handling code here:
        btnPPCount++;
        if (btnPPCount % 2 == 0) {
            btnPlayPause.setIcon(new ImageIcon(getClass().getResource("/ToolIcon/1369380862_play.png")));
            miPP.setText("Play Video");
            if (embededMPC != null) {
                embededMPC.getMediaPlayer().pause();
            }
        } else {
            btnPlayPause.setIcon(new ImageIcon(getClass().getResource("/ToolIcon/1369380612_pause.png")));
            miPP.setText("Pause Video");
            if (embededMPC != null) {
                embededMPC.getMediaPlayer().play();
            }
        }
    }

    public void playNextVideo() {
        // TODO add your handling code here:
        int index = resultList.getSelectedIndex() + 1;
        resultList.setSelectedIndex(index);
        playVideo(index);
        countBtnListPerformed++;
    }

    public void loadSearchChannelToList() {
        if (scrollResultList != null) {
            pnResult.remove(scrollResultList);
        }
        if (dataPanels != null && dataPanels.length > 0) {
            btnList.setEnabled(true);
            renderer = new HoverListCellRenderer();
            resultList = new JList(dataPanels);
            resultList.setCellRenderer(renderer);
            resultList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            resultList.setFixedCellWidth(350);
            resultList.setVisibleRowCount(50);
            resultList.addMouseListener(renderer.getHandler(resultList));
            resultList.addMouseMotionListener(renderer.getHandler(resultList));
            resultList.setCursor(new Cursor(Cursor.HAND_CURSOR));


            resultList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    final MouseEvent final_evt = evt;
                    if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 1) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                int index = resultList.locationToIndex(final_evt.getPoint());
                                if (embededMPC != null && embededMPC.getMediaPlayer().isPlaying()) {
                                    embededMPC.getMediaPlayer().stop();
                                }
                                loadChannelToPanelResult(channelResults.get(index).getUri(), channelResults.get(index).getChannelID());
                                lblTitle.setText("<html>" + channelResults.get(index).getTitle() + "</html>");
                                controlButton(false);
                            }
                        }).start();

                    }
                }
            });

            scrollResultList = new JScrollPane(resultList);
            scrollResultList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            pnResult.setLayout(borderLayout);
            pnResult.add(scrollResultList, BorderLayout.EAST);
            pnResult.revalidate();
        }
    }

    public void controlButton(boolean value) {
        btnStop.setEnabled(value);
        btnNext.setEnabled(value);
        btnPlayPause.setEnabled(value);
        btnPrevious.setEnabled(value);
        btnStop.setEnabled(value);
    }

    public synchronized void loadChannelToPanelResult(String channelURL, String channelID) {
        try {
            Desktube.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            pnChannel = new JPanel();
            pnChannel.setLayout(new BoxLayout(pnChannel, BoxLayout.PAGE_AXIS));
            httpclient = new DefaultHttpClient();
            httpget = new HttpGet("http://www.youtube.com" + channelURL);
            responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpget, responseHandler);
            String startStrC4BOX = "<div class=\"compact-shelf shelf-item yt-uix-shelfslider yt-uix-shelfslider-at-head yt-uix-shelfslider-at-tail c4-box\" >";
            int index1 = 0;
            int index2 = 0;
            Vector<String> c4BOXList = new Vector<>();

            while (true) {
                int index = index1;
                index1 = responseBody.indexOf(startStrC4BOX, index1 + 1);

                if (index1 < 0) {
                    startStrC4BOX = "<div class=\"expanded-shelf shelf-item c4-box\" >";
                    index1 = responseBody.indexOf(startStrC4BOX, index + 1);
                    if (index1 < 0) {
                        break;
                    } else {
                        index2 = responseBody.indexOf("</li>\n"
                                + "    </ul>\n"
                                + "  </div>", index1 + startStrC4BOX.length());
                        String temp = responseBody.substring(index1 + startStrC4BOX.length(), index2);
                        c4BOXList.add(temp);
                    }
                } else {
                    index2 = responseBody.indexOf("</span></button>\n"
                            + "\n"
                            + "  </div>", index1 + startStrC4BOX.length());
                    if (index2 < index1) {
                        index2 = responseBody.indexOf("</li>\n"
                                + "    </ul>\n"
                                + "  </div>", index1 + startStrC4BOX.length());
                    }
                    if (index2 > 0) {
                        String temp = responseBody.substring(index1 + startStrC4BOX.length(), index2);
                        c4BOXList.add(temp);
                    } else {
                        break;
                    }
                }
            }
            for (int i = 0; i < c4BOXList.size(); i++) {
                JPanel pnModule = new JPanel(new BorderLayout());
                final Vector<VideoInfo> videoInfos = new Vector<>();
                index1 = 0;
                index2 = 0;
                String c4Box = c4BOXList.get(i).toString();
                index2 = c4Box.indexOf("</a>", index1);
                String moduleTitle = c4Box.substring(index1, index2);

                index1 = moduleTitle.indexOf("title=\"", 0);
                index2 = moduleTitle.indexOf("\"", index1 + "title=\"".length());
                if (index1 >= 0 && index2 > index1) {
                    moduleTitle = moduleTitle.substring(index1 + "title=\"".length(), index2);
                }
                index1 = c4Box.indexOf("href=\"", 0);
                index2 = c4Box.indexOf("\"", index1 + "href=\"".length());
                String moduleURL = "";
                if (index1 >= 0 && index2 > index1) {
                    moduleURL = c4Box.substring(index1 + "href=\"".length(), index2);
                }



                String startStrVideo = "<span class=\"context-data-item\"";
                index1 = 0;
                int count = 0;
                Vector<String> videos = new Vector<>();
                while (count <= 10) {
                    index1 = c4Box.indexOf(startStrVideo, index1 + startStrVideo.length());
                    index2 = c4Box.indexOf("<span class=\"vertical-align\"></span>", index1 + startStrVideo.length());

                    if (index1 >= 0 && index2 > index1) {
                        String video_info = c4Box.substring(index1 + startStrVideo.length(), index2);
                        videos.add(video_info);
                        count++;
                    } else {
                        break;
                    }
                }
                if (index1 < 0) {
                    startStrVideo = " <div class=\"feed-item-content-wrapper clearfix context-data-item\"";
                    while (true) {
                        index1 = c4Box.indexOf(startStrVideo, index1 + startStrVideo.length());
                        index2 = c4Box.indexOf(">", index1 + startStrVideo.length());

                        if (index1 >= 0 && index2 > index1) {
                            String video_info = c4Box.substring(index1 + startStrVideo.length(), index2);
                            videos.add(video_info);
                            count++;
                        } else {
                            break;
                        }
                    }
                }
                for (String video : videos) {
                    //<editor-fold defaultstate="collapsed" desc="add to videoInfos">
                    String result = video;
                    String startStrId = "data-context-item-id=\"";
                    String startStrTitle = "data-context-item-title=\"";
                    String startStrTime = "data-context-item-time=\"";
                    String startStrUser = "data-context-item-user=\"";
                    String startStrView = "data-context-item-views=\"";
                    String id = "";
                    String title = "";
                    String time = "";
                    String user = "";
                    String view = "";
                    int check = 0;
                    index1 = 0;
                    index1 = result.indexOf(startStrView, index1 + 1);
                    if (index1 < 0) {
                        break;
                    } else {
                        index2 = result.indexOf("\"", index1 + startStrView.length());
                        if (index2 > 0) {
                            view = result.substring(index1 + startStrView.length(), index2);
                            index1 = 0;
                            check++;
                        } else {
                            break;
                        }
                    }

                    index1 = result.indexOf(startStrTitle, index1 + 1);
                    if (index1 < 0) {
                        break;
                    } else {
                        index2 = result.indexOf("\"", index1 + startStrTitle.length());
                        if (index2 > 0) {
                            title = result.substring(index1 + startStrTitle.length(), index2);
                            index1 = 0;
                            check++;
                        } else {
                            break;
                        }
                    }

                    index1 = result.indexOf(startStrUser, index1 + 1);
                    if (index1 < 0) {
                        break;
                    } else {
                        index2 = result.indexOf("\"", index1 + startStrUser.length());
                        if (index2 > 0) {
                            user = result.substring(index1 + startStrUser.length(), index2);
                            index1 = 0;
                            check++;
                        } else {
                            break;
                        }
                    }

                    index1 = result.indexOf(startStrTime, index1 + 1);
                    if (index1 < 0) {
                        break;
                    } else {
                        index2 = result.indexOf("\"", index1 + startStrTime.length());
                        if (index2 > 0) {
                            time = result.substring(index1 + startStrTime.length(), index2);
                            index1 = 0;
                            check++;
                        } else {
                            break;
                        }
                    }

                    index1 = result.indexOf(startStrId, index1 + 1);
                    if (index1 < 0) {
                        break;
                    } else {
                        index2 = result.indexOf("\"", index1 + startStrId.length());
                        if (index2 > 0) {
                            id = result.substring(index1 + startStrId.length(), index2);
                            index1 = 0;
                            check++;
                        } else {
                            break;
                        }
                    }
                    String imgUrl = "http://i1.ytimg.com/vi/" + id + "/mqdefault.jpg";
                    VideoInfo info = new VideoInfo(id, title, time, user, imgUrl, view);
                    videoInfos.add(info);

                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="create panel channel">
                dataPanels = getPanelsVideoInChannel(videoInfos);
                renderer = new HoverListCellRenderer();
                final JList listChannel = new JList(dataPanels);
                listChannel.setBackground(new Color(237, 237, 237));
                listChannel.setCellRenderer(renderer);
                listChannel.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                listChannel.setVisibleRowCount(1);
                listChannel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                listChannel.setFixedCellWidth(240);
                listChannel.addMouseListener(renderer.getHandler(listChannel));
                listChannel.addMouseMotionListener(renderer.getHandler(listChannel));
                listChannel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                listChannel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        final MouseEvent final_evt = evt;
                        if (evt.getClickCount() == 2) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    int index = listChannel.locationToIndex(final_evt.getPoint());
                                    pnResult.removeAll();
                                    videoResults = videoInfos;
                                    loadVideoDataToPanel();
                                    loadSearchVideoToList(index);
                                    loadPlayerToPanel();
                                    playVideo(index);
                                }
                            }).start();

                        }
                    }
                });
                JPanel jpanel = new JPanel(new BorderLayout());
                jpanel.add(listChannel, BorderLayout.CENTER);

                if (dataPanels.length > 0) {
                    final JLabel lblModuleTitle = new JLabel();
                    lblModuleTitle.setText("<html><span>" + moduleTitle + "</span></html>");
                    lblModuleTitle.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    lblModuleTitle.setForeground(new Color(0, 0, 0));
                    lblModuleTitle.setBorder(new EmptyBorder(0, 20, 0, 0));

                    final String title = moduleTitle;
                    final String url = moduleURL;
                    final String id = channelID;
                    //<editor-fold defaultstate="collapsed" desc="title module event">
                    lblModuleTitle.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            playOrShowAllVideoFromModule(url, title, id);
                            Desktube.this.setTitle("Youtube - Playlist: " + title);
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            lblModuleTitle.setText("<html><u>" + title + "</u></html>");
                            lblModuleTitle.setToolTipText("Show all video from \"" + title + "\"");
                            LblTitleVideo.setText("Show all video from \"" + title + "\"");
                            lblModuleTitle.setForeground(Color.BLACK);
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            lblModuleTitle.setText("<html><span>" + title + "</span></html>");
                            LblTitleVideo.setText("");
                            lblModuleTitle.setForeground(new Color(0, 0, 0));
                        }
                    });
                    //</editor-fold>
                    lblModuleTitle.setFont(new Font("arial", Font.BOLD, 16));

                    pnModule.add(lblModuleTitle, BorderLayout.NORTH);
                    pnModule.add(jpanel, BorderLayout.CENTER);
                    JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);

                    pnModule.add(sep, BorderLayout.SOUTH);

                }

                pnChannel.add(pnModule);
                //</editor-fold> 
            }
            scrChannel = new JScrollPane(pnChannel);
            scrChannel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrChannel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            pnBrowser = new JPanel(new BorderLayout());
            pnBrowser.add(scrChannel, BorderLayout.CENTER);
            pnResult.removeAll();
            pnResult.add(pnBrowser, BorderLayout.CENTER);
            showList();
            pnResult.revalidate();

        } catch (IOException ex) {
            Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Desktube.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }
        }
    }

    public synchronized void searching() {
        // TODO add your handling code here:
        setPlayerType(false);
        lblBackPage.setVisible(false);
        txtSearch.setText(txtSearchDef.getText());
        search = new Thread(new Runnable() {

            @Override
            public void run() {
                if (!txtSearchDef.getText().equals("")) {
                usingCombobox = cbbFilter;
                query = txtSearchDef.getText();
                    switch (cbbFilter.getSelectedItem().toString()) {
                        case "Video":
                            try {
                                setPlayerType(false);
                                Desktube.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                                loadSearchResultToDataPanels(txtSearchDef.getText(), pageIndex, cbbFilter.getSelectedItem().toString());
                                loadVideoDataToPanel();
                                loadSearchVideoToList(0);
                                showPageIndex();

                                if (embededMPC == null) {
                                    loadPlayerToPanel();
                                }
                                playVideo(resultList.getSelectedIndex());
                            } catch (IOException | URISyntaxException ex) {
                                Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                Desktube.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            }
                            break;
                        case "Channel":
                            try {
                                setPlayerType(false);
                                Desktube.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                                loadSearchResultToDataPanels(txtSearchDef.getText(), pageIndex, cbbFilter.getSelectedItem().toString());
                                pnResult.removeAll();
                                loadChannelDataToPanel();
                                loadSearchChannelToList();
                                pnResult.setBackground(Color.BLACK);
                                showPageIndex();
                            } catch (IOException | URISyntaxException ex) {
                                Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                Desktube.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            }
                            break;
                        case "Playlist":
                            try {

                                Desktube.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                                loadSearchResultToDataPanels(txtSearchDef.getText(), pageIndex, cbbFilter.getSelectedItem().toString());
                                pnResult.removeAll();
                                loadPlaylistDataToPanel();
                                loadSearchPlaylistToList();

                                pnResult.setBackground(Color.BLACK);

                                showPageIndex();
                            } catch (IOException | URISyntaxException ex) {
                                Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                Desktube.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            }
                            break;
                    }
            }
                
            }
        });
        search.start();
        
    }

    private void playOrShowAllVideoFromModule(String url, String title, String channelID) {
        final String strURL = url;
        final String moduleTitle = title;
        final String channel_id = channelID;
        int index1 = url.indexOf("sort=");
        String sort_ = "";
        countBtnShow = 1;

        if (index1 > 0 && index1 <= url.length() - 8) {
            sort_ = url.substring(index1 + 5, url.indexOf("&", index1 + 5));
        } else if (index1 > 0 && index1 > url.length() - 8) {
            sort_ = url.substring(index1 + 5, url.length());
        }
        final String sort = sort_;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Desktube.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    httpclient = new DefaultHttpClient();
                    httpget = new HttpGet("http://www.youtube.com" + strURL);
                    responseHandler = new BasicResponseHandler();
                    String responseBody = httpclient.execute(httpget, responseHandler);
                    if (strURL.indexOf("/playlist?") >= 0) {
                        //<editor-fold defaultstate="collapsed" desc="playlist">
                        Vector<VideoInfo> videoInfos = new Vector<>();
                        String olContent = "";
                        Vector<String> listItem = new Vector<>();
                        int index1 = responseBody.indexOf("<ol>", 0);
                        int index2 = responseBody.indexOf("</ol>", index1 + 4);
                        if (index1 >= 0 && index2 > index1) {
                            olContent = responseBody.substring(index1, index2);
                        }
                        String startStr = "<div class=\"thumb-container\">";
                        index1 = 0;
                        while (true) {
                            index1 = olContent.indexOf(startStr, index1 + 1);
                            index2 = olContent.indexOf("</p>", index1 + startStr.length());
                            if (index1 >= 0 && index2 > index1) {
                                String item = olContent.substring(index1, index2);
                                listItem.add(item);
                            } else {
                                break;
                            }
                        }
                        String title = "", id = "", user = "", imgUrl = "", viewCount = "", time = "";
                        String startStrTitle = "title=\"";
                        String startStrID = "/watch?v=";
                        String startStrUser = "dir=\"ltr\">";
                        String startStrView = "<span class=\"video-view-count\">";
                        String startStrTime = "<span class=\"video-time\">";
                        index1 = 0;
                        index2 = 0;
                        for (String item : listItem) {
                            //<editor-fold defaultstate="collapsed" desc="cut info">
                            index1 = item.indexOf(startStrTitle, 0);
                            index2 = item.indexOf("\"", index1 + startStrTitle.length());
                            if (index1 >= 0 && index2 > index1) {
                                title = item.substring(index1 + startStrTitle.length(), index2);
                            } else {
                                break;
                            }

                            index1 = item.indexOf(startStrID, 0);
                            index2 = item.indexOf("&", index1 + startStrID.length());
                            if (index1 >= 0 && index2 > index1) {
                                id = item.substring(index1 + startStrID.length(), index2);
                            } else {
                                break;
                            }

                            index1 = item.indexOf(startStrUser, item.indexOf("by"));
                            index2 = item.indexOf("</a>", index1 + startStrUser.length());
                            if (index1 >= 0 && index2 > index1) {
                                user = item.substring(index1 + startStrUser.length(), index2);
                            } else {
                                break;
                            }

                            index1 = item.indexOf(startStrView, 0);
                            index2 = item.indexOf("</span>", index1 + startStrView.length());
                            if (index1 >= 0 && index2 > index1) {
                                viewCount = item.substring(index1 + startStrView.length(), index2);
                                viewCount = viewCount.replaceAll("\n", "").replaceAll(" ", "");
                            } else {
                                break;
                            }

                            index1 = item.indexOf(startStrTime, 0);
                            index2 = item.indexOf("</span>", index1 + startStrTime.length());
                            if (index1 >= 0 && index2 > index1) {
                                time = item.substring(index1 + startStrTime.length(), index2);
                            } else {
                                break;
                            }
                            //</editor-fold>
                            imgUrl = "http://i1.ytimg.com/vi/" + id + "/mqdefault.jpg";
                            VideoInfo info = new VideoInfo(id, title, time, user, imgUrl, viewCount);
                            videoInfos.add(info);
                        }
                        hidePageIndex();
                        videoResults = videoInfos;
                        loadVideoDataToPanel();
                        loadSearchVideoToList(0);
                        loadPlayerToPanel();
                        lblBackPage.setVisible(true);
                        if (embededMPC == null) {
                            loadPlayerToPanel();
                        }
                        playVideo(resultList.getSelectedIndex());
                        setPlayerType(true);
                        //</editor-fold>
                    } else if (strURL.indexOf("/user/") >= 0) {
                        Vector<VideoInfo> videoInfos = createDataVideoItem(responseBody);
                        dataPanels = getPanelsVideoInChannel(videoInfos);
                        new_VideoInfo = videoInfos;
                        //<editor-fold defaultstate="collapsed" desc="add video to pnBrowser">
                        JPanel pnModuleDetail = new JPanel(new BorderLayout());
                        JLabel lblModuleTitle = new JLabel();
                        lblModuleTitle.setText("<html><span>" + moduleTitle + "</span></html>");
                        lblModuleTitle.setBorder(new EmptyBorder(0, 20, 0, 0));
                        lblModuleTitle.setFont(new Font("Arial", Font.BOLD, 18));
                        lblModuleTitle.setForeground(new Color(0, 0, 0));

                        JLabel lblBefore = new JLabel();
                        lblBefore.setIcon(new ImageIcon(getClass().getResource("/ToolIcon/before.png")));
                        lblBefore.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        lblBefore.setBorder(new EmptyBorder(0, 10, 0, 0));
                        lblBefore.setToolTipText("Back");
                        lblBefore.addMouseListener(new MouseAdapter() {
                            public void mouseClicked(MouseEvent evt) {
                                pnBrowser = new JPanel(new BorderLayout());
                                pnBrowser.add(scrChannel, BorderLayout.CENTER);
                                pnResult.removeAll();
                                pnResult.add(pnBrowser, BorderLayout.CENTER);
                                showList();
                                pnResult.revalidate();
                            }
                        });

                        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                        panel.add(lblBefore);
                        panel.add(lblModuleTitle);

                        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);

                        JPanel pnModuleTitle = new JPanel(new BorderLayout());

                        pnModuleTitle.add(panel, BorderLayout.CENTER);
                        pnModuleTitle.add(sep, BorderLayout.SOUTH);

                        renderer = new HoverListCellRenderer();
                        final JList listChannel = new JList(dataPanels);
                        listChannel.setBackground(new Color(237, 237, 237));
                        listChannel.setCellRenderer(renderer);
                        listChannel.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                        listChannel.setVisibleRowCount(-1);
                        listChannel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                        listChannel.setFixedCellWidth(240);
                        listChannel.addMouseListener(renderer.getHandler(listChannel));
                        listChannel.addMouseMotionListener(renderer.getHandler(listChannel));
                        listChannel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        listChannel.addMouseListener(new MouseAdapter() {
                            public void mouseClicked(MouseEvent evt) {
                                final MouseEvent final_evt = evt;
                                if (evt.getClickCount() == 2) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Desktube.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                                            int index = listChannel.locationToIndex(final_evt.getPoint());
                                            pnResult.removeAll();
                                            videoResults = new_VideoInfo;
                                            loadVideoDataToPanel();
                                            loadSearchVideoToList(index);
                                            loadPlayerToPanel();
                                            lblBackPage.setVisible(true);
                                            playVideo(index);
                                            setPlayerType(true);
                                            Desktube.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                        }
                                    }).start();
                                }
                            }
                        });
                        JScrollPane scrPane = new JScrollPane(listChannel);
                        final JButton btnShowMore = new JButton("Show more");
                        btnShowMore.setFocusPainted(false);
                        final String channelId = channel_id;
                        final Vector<VideoInfo> final_info = videoInfos;
                        btnShowMore.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Desktube.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                                        countBtnShow++;
                                        String urlShowMore = "http://www.youtube.com/c4_browse_ajax?action_load_more_videos=1&paging=" + countBtnShow + "&sort=" + sort + "&live_view=500&flow=grid&view=0&channel_id=" + channelId + "&fluid=True";
                                        httpclient = new DefaultHttpClient();
                                        httpget = new HttpGet(urlShowMore);
                                        responseHandler = new BasicResponseHandler();
                                        new_VideoInfo = final_info;
                                        try {
                                            String responseBody = httpclient.execute(httpget, responseHandler);
                                            JSONObject jObj = new JSONObject(responseBody);
                                            String moreVideo = jObj.getString("content_html");
                                            Vector<VideoInfo> videoInfos = createDataVideoItem(moreVideo);
                                            for (VideoInfo videoInfo : videoInfos) {
                                                new_VideoInfo.add(videoInfo);
                                            }
                                            dataPanels = getPanelsVideoInChannel(new_VideoInfo);
                                            listChannel.setListData(dataPanels);
                                            listChannel.revalidate();
                                            if (videoInfos.size() < 30) {
                                                btnShowMore.setVisible(false);
                                            }
                                        } catch (IOException ex) {
                                            Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
                                        } finally {
                                            if (httpclient != null) {
                                                httpclient.getConnectionManager().shutdown();
                                            }
                                            Desktube.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                        }
                                    }
                                }).start();
                            }
                        });
                        pnModuleDetail.add(pnModuleTitle, BorderLayout.NORTH);
                        pnModuleDetail.add(scrPane, BorderLayout.CENTER);
                        pnModuleDetail.add(btnShowMore, BorderLayout.SOUTH);
                        pnModuleDetail.revalidate();
                        pnBrowser.removeAll();
                        pnBrowser.setLayout(new BorderLayout());
                        pnBrowser.add(pnModuleDetail, BorderLayout.CENTER);
                        pnBrowser.revalidate();
                        //</editor-fold>
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (httpclient != null) {
                        httpclient.getConnectionManager().shutdown();
                    }
                    Desktube.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        }).start();

    }

    public Vector<VideoInfo> createDataVideoItem(String responseBody) {
        Vector<String> listItem = new Vector<>();
        Vector<VideoInfo> videoInfos = new Vector<>();
        int index1 = 0, index2 = 0;
        String startStrListItem = "<span class=\"context-data-item\"";
        String startStrId = "data-context-item-id=\"";
        String startStrTitle = "data-context-item-title=\"";
        String startStrTime = "data-context-item-time=\"";
        String startStrUser = "data-context-item-user=\"";
        String startStrView = "data-context-item-views=\"";
        String id = "";
        String title = "";
        String time = "";
        String user = "";
        String view = "";


        while (true) {
            index1 = responseBody.indexOf(startStrListItem, index1 + 1);
            index2 = responseBody.indexOf(">", index1 + startStrListItem.length());
            if (index1 >= 0 && index2 > index1) {
                String item = responseBody.substring(index1, index2);
                listItem.add(item);
            } else {
                break;
            }
        }
        index1 = 0;
        index2 = 0;
        for (String item : listItem) {
            index1 = item.indexOf(startStrId, 0);
            index2 = item.indexOf("\"", index1 + startStrId.length());
            id = item.substring(index1 + startStrId.length(), index2);

            index1 = item.indexOf(startStrTitle, 0);
            index2 = item.indexOf("\"", index1 + startStrTitle.length());
            title = item.substring(index1 + startStrTitle.length(), index2);

            index1 = item.indexOf(startStrTime, 0);
            index2 = item.indexOf("\"", index1 + startStrTime.length());
            time = item.substring(index1 + startStrTime.length(), index2);

            index1 = item.indexOf(startStrUser, 0);
            index2 = item.indexOf("\"", index1 + startStrUser.length());
            user = item.substring(index1 + startStrUser.length(), index2);

            index1 = item.indexOf(startStrView, 0);
            index2 = item.indexOf("\"", index1 + startStrView.length());
            view = item.substring(index1 + startStrView.length(), index2);

            String imgUrl = "http://i1.ytimg.com/vi/" + id + "/mqdefault.jpg";

            VideoInfo info = new VideoInfo(id, title, time, user, imgUrl, view);
            videoInfos.add(info);
        }
        Vector<VideoInfo> infos = videoInfos;
        return infos;
    }

    public void doFullSscreen(){
        // TODO add your handling code here:
        if (embededMPC != null) {
            countFScreen++;
            long time = 0;
            JPanel pnVideo = pnBrowser;
            time = embededMPC.getMediaPlayer().getTime();
            embededMPC.getMediaPlayer().stop();
        
        if (countFScreen % 2 == 0) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            if (fullscreenFrame == null) {
                fullscreenFrame = new JFrame();
                fullscreenFrame.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent evt) {
                        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            doFullSscreen();
                        }
                    }
                });

                fullscreenFrame.getContentPane().add(pnVideo);
                fullscreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                fullscreenFrame.setUndecorated(true);
                fullscreenFrame.setBounds(0, 0, screenSize.width, screenSize.height);
                btnFullS.setIcon(new ImageIcon(getClass().getResource("/ToolIcon/exit_fullscreen.png")));
                this.setVisible(false);

                fullscreenFrame.setVisible(true);
                if (embededMPC != null) {
                    embededMPC.getMediaPlayer().start();
                    embededMPC.getMediaPlayer().setTime(time);
                }
                
            } else {
                btnFullS.setIcon(new ImageIcon(getClass().getResource("/ToolIcon/exit_fullscreen.png")));
                fullscreenFrame.getContentPane().add(pnVideo);
                fullscreenFrame.setVisible(true);
                this.setVisible(false);
                if (embededMPC != null) {
                    embededMPC.getMediaPlayer().start();
                    embededMPC.getMediaPlayer().setTime(time);
                }

            }
            
        } else {
            if (fullscreenFrame != null) {
                btnFullS.setIcon(new ImageIcon(getClass().getResource("/ToolIcon/fullscreen.png")));
                fullscreenFrame.setVisible(false);
                this.setContentPane(this.pnContent);
                pnResult.add(pnBrowser, BorderLayout.CENTER);
                this.setVisible(true);
                if (embededMPC != null) {
                    embededMPC.getMediaPlayer().start();
                    embededMPC.getMediaPlayer().setTime(time);
                }
            }
        }
    }
}
    private void loadPlaylistDataToPanel() {
        if (playlistResults != null) {
            dataPanels = new JPanel[playlistResults.size()];
            JLabel image;
            JLabel lblInfo;
            String html;
            String infomation;
            JPanel panel;
            for (int i = 0; i < playlistResults.size(); i++) {
                String title;
                if (playlistResults.get(i).getTitle().length() > 150) {
                    title = playlistResults.get(i).getTitle().substring(0, 150) + " ...";
                } else {
                    title = playlistResults.get(i).getTitle();
                }
                image = new JLabel();
                lblInfo = new JLabel();
                html = "<html>"
                        + "	<img src=\"" + playlistResults.get(i).getImage_url() + "\"  height=\"85\" width=\"125\">"
                        + "</html>";
                image.setText(html);
                image.setPreferredSize(new Dimension(125, 85));
                infomation = "<html><b>&nbsp;" + title + "</b>"
                        + "<br/>&nbsp;&nbsp;&nbsp;Uploaded by: " + playlistResults.get(i).getUploader()
                        + "<br/>&nbsp;&nbsp;&nbsp;" + playlistResults.get(i).getVideo_count() + " videos"
                        + "</html>";

                lblInfo.setText(infomation);
                panel = new JPanel(new BorderLayout());
                panel.add(image, BorderLayout.EAST);
                panel.add(lblInfo, BorderLayout.CENTER);
                dataPanels[i] = panel;
            }

        }
    }

    private synchronized void loadSearchPlaylistToList() {
        if (scrollResultList != null) {
            pnResult.remove(scrollResultList);
//            pnResult.revalidate();
        }
        if (dataPanels.length > 0) {
            renderer = new HoverListCellRenderer();
            resultList = new JList(dataPanels);
            resultList.setCellRenderer(renderer);
            resultList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            resultList.setFixedCellWidth(350);
            resultList.setVisibleRowCount(200);
            resultList.addMouseListener(renderer.getHandler(resultList));
            resultList.addMouseMotionListener(renderer.getHandler(resultList));
            resultList.setCursor(new Cursor(Cursor.HAND_CURSOR));
            resultList.setComponentPopupMenu(popupMenu);
            resultList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    if (SwingUtilities.isRightMouseButton(evt)) {
                        int index = resultList.locationToIndex(evt.getPoint());
                        resultList.setSelectedIndex(index);
                    } else if (SwingUtilities.isMiddleMouseButton(evt)) {
                    } else if (evt.getClickCount() == 1) {
                        final int index = resultList.locationToIndex(evt.getPoint());
                        playVideoFromSearchPlaylist(index);
                    }

                }
            });
            scrollResultList = new JScrollPane(resultList);
            scrollResultList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            
            pnResult.setLayout(borderLayout);
            pnResult.add(scrollResultList, BorderLayout.EAST);
//            pnResult.revalidate();
        }
    }

    public synchronized void paging(JComboBox cbb) {
        try {
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            loadSearchResultToDataPanels(query, pageIndex, cbb.getSelectedItem().toString());
            switch (cbb.getSelectedItem().toString()) {
                case "Video":
                    loadVideoDataToPanel();
                    pnResult.remove(scrollResultList);
                    resultList.setListData(dataPanels);
                    showList();
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    resultList.ensureIndexIsVisible(0);
                    showPageIndex();
                    resultList.repaint();
                    break;
                case "Channel":
                    loadChannelDataToPanel();
                    pnResult.remove(scrollResultList);
                    resultList.setListData(dataPanels);
                    showList();
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    resultList.ensureIndexIsVisible(0);
                    showPageIndex();
                    resultList.repaint();
                    break;
                case "Playlist":
                    loadPlaylistDataToPanel();
                    pnResult.remove(scrollResultList);
                    resultList.setListData(dataPanels);
                    showList();
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    resultList.ensureIndexIsVisible(0);
                    showPageIndex();
                    resultList.repaint();
                    break;
            }
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showVideoFromPlaylist(String pID) {
        int index1 = 0,index2 = 0,index3 = 0,index4 = 0;
        try {
            httpclient = new DefaultHttpClient();
            httpget = new HttpGet("http://www.youtube.com/playlist?list=" + pID);
            responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpget, responseHandler);
            String startStr = "<div class=\"thumb-container\">";
            Vector<VideoInfo> videoInfos = new Vector<>();

            //<editor-fold defaultstate="collapsed" desc="load data to Video Result">
            while (true) {
                index1 = responseBody.indexOf(startStr, index1 + 1);
                index2 = responseBody.indexOf("<p class=\"video-description yt-ui-ellipsis yt-ui-ellipsis-2\">", index1 + startStr.length());
                if (index1 >= 0 && index2 > index1) {
                    String temp = responseBody.substring(index1 + startStr.length(), index2);
                    String startStrVID = "<a href=\"/watch?v=";
                    String startStrTitle = "<span class=\"title video-title\" dir=\"ltr\">";
                    String startStrTime = "<span class=\"video-time\">";
                    String startStrVCount = "<span class=\"video-view-count\">";
                    String startStrUploader = "dir=\"ltr\">";
                    String vid = "", title = "", time = "", vcount = "", uploader = "", img = "";



                    index3 = temp.indexOf(startStrVID, 0);
                    index4 = temp.indexOf("&amp;", index3 + startStrVID.length());
                    if (index3 >= 0 && index4 > index3) {
                        vid = temp.substring(index3 + startStrVID.length(), index4);
                    }

                    index3 = temp.indexOf(startStrTitle, 0);
                    index4 = temp.indexOf("</span>", index3 + startStrTitle.length());
                    if (index3 >= 0 && index4 > index3) {
                        title = temp.substring(index3 + startStrTitle.length(), index4);
                    }

                    index3 = temp.indexOf(startStrTime, 0);
                    index4 = temp.indexOf("</span>", index3 + startStrTime.length());
                    if (index3 >= 0 && index4 > index3) {
                        time = temp.substring(index3 + startStrTime.length(), index4);
                    }

                    index3 = temp.indexOf(startStrVCount, 0);
                    index4 = temp.indexOf("</span>", index3 + startStrVCount.length());
                    if (index3 >= 0 && index4 > index3) {
                        vcount = temp.substring(index3 + startStrVCount.length(), index4).replaceAll("\n", "").replaceAll(" ", "");
                    }

                    index3 = temp.indexOf(startStrUploader, temp.indexOf(title, title.length()));
                    index4 = temp.indexOf("</a>", index3 + startStrUploader.length());
                    if (index3 >= 0 && index4 > index3) {
                        uploader = temp.substring(index3 + startStrUploader.length(), index4);
                    } else {
                        break;
                    }

                    img = "http://i1.ytimg.com/vi/" + vid + "/mqdefault.jpg";
                    VideoInfo info = new VideoInfo(vid, title, time, uploader, img, vcount);
                    videoInfos.add(info);
                } else {
                    break;
                }
            }
            //</editor-fold>
            videoResults = videoInfos;
            loadVideoDataToPanel();
            loadSearchVideoToList(-1);
            if (embededMPC == null) {
                loadPlayerToPanel();
            }
        } catch (IOException ex) {
            Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }
        }
    }

    public String convertXmlToSrt(String xml_sub) {
        int index = xml_sub.indexOf("<", 0);
        if (index < 0) {
            return "1\n"
                    + "00:00:00.0 --> 00:00:05.0"
                    + "null";
        }
        xml_sub = xml_sub.replace("</text>", "\n\n").replaceAll("<transcript>", "").replaceAll("</transcript>", "");
        xml_sub = xml_sub.replace(xml_sub.substring(index, xml_sub.indexOf(">", index) + 1), "");
        int index1 = -1, index2 = 0, count = 0;
        while (true) {
            index1 = xml_sub.indexOf("<text", index1 + 1);
            if (index1 >= 0) {
                count++;
                String start = "<text start=\"";
                String end = "dur=\"";
                int indexOfStart = xml_sub.indexOf(start, index1);
                int indexOfEnd = xml_sub.indexOf("\"", indexOfStart + start.length());
                float startTime = Float.parseFloat(xml_sub.substring(indexOfStart + start.length(), indexOfEnd));
                String startTimeStr = "", endTimeStr = "";
                indexOfStart = xml_sub.indexOf(end, index1);
                indexOfEnd = xml_sub.indexOf("\"", indexOfStart + end.length());
                float endTime = Float.parseFloat(xml_sub.substring(indexOfStart + end.length(), indexOfEnd));
                int hour = 0, min = 0;
                float sec = 0;
                if (startTime < 3600) {
                    min = (int) (startTime / 60);
                    sec = startTime % 60;
                } else if (startTime >= 3600) {
                    hour = (int) (startTime / 3600);
                    min = (int) ((startTime % 3600) / 60);
                    sec = (startTime % 3600) % 60;
                }
                startTimeStr = (hour < 10 ? "0" + hour : hour) + ":" + (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);

                endTime = startTime + endTime;
                if (endTime < 3600) {
                    min = (int) (endTime / 60);
                    sec = endTime % 60;
                } else if (endTime >= 3600) {
                    hour = (int) (endTime / 3600);
                    min = (int) ((endTime % 3600) / 60);
                    sec = (endTime % 3600) % 60;
                }
                endTimeStr = (hour < 10 ? "0" + hour : hour) + ":" + (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
                
                xml_sub = xml_sub.replaceFirst("<text start=\"", "" + count + "\n" + startTimeStr + " --> " + endTimeStr);
                String delString = xml_sub.substring(xml_sub.indexOf(endTimeStr) + endTimeStr.length(), xml_sub.indexOf(">", xml_sub.indexOf(endTimeStr) + endTimeStr.length()) + 1);
                xml_sub = xml_sub.replaceFirst(delString, "\n");
            } else {
                break;
            }
        }
        return xml_sub;

    }

    public synchronized void secondarySearching() {
        setPlayerType(false);
        lblBackPage.setVisible(false);
        if (!txtSearch.getText().equals("")) {
            usingCombobox = cbbFilter2;
            query = txtSearch.getText();
            pageIndex = 1;
            search = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Desktube.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                        loadSearchResultToDataPanels(txtSearch.getText(), pageIndex, cbbFilter2.getSelectedItem().toString());
                        switch (cbbFilter2.getSelectedItem().toString()) {
                            case "Video":
                                setPlayerType(false);
                                loadVideoDataToPanel();
                                loadSearchVideoToList(0);
                                if (embededMPC == null) {
                                    loadPlayerToPanel();
                                }
                                if (!embededMPC.getMediaPlayer().isPlaying()) {
                                    playVideo(resultList.getSelectedIndex());
                                }
                                break;
                            case "Channel":
                                setPlayerType(false);
                                if (pnDefault != null) {
                                    pnResult.remove(pnDefault);
                                }
                                loadChannelDataToPanel();
                                pnResult.setBackground(Color.BLACK);
                                loadSearchChannelToList();
                                break;
                            case "Playlist":
                                loadPlaylistDataToPanel();
                                if (pnDefault != null) {
                                    pnResult.remove(pnDefault);
                                }
                                pnResult.setBackground(Color.BLACK);
                                loadSearchPlaylistToList();
                                break;
                        }
                        pnResult.revalidate();
                        showList();
                        countBtnListPerformed = 1;
                        showPageIndex();
                    } catch (IOException | URISyntaxException ex) {
                        Logger.getLogger(Desktube.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        Desktube.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            });
            search.start();
        }
    }

    public void playVideoFromSearchPlaylist(final int index) {
        if (index >= 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    hidePageIndex();
                    lblTitle.setText("<html>" + playlistResults.get(index).getTitle() + "</html>");
                    Desktube.this.setTitle(playlistResults.get(index).getTitle());
                    String pID = playlistResults.get(index).getPlaylisID();
                    showVideoFromPlaylist(pID);
                    setPlayerType(true);
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }).start();
        }
    }
}
