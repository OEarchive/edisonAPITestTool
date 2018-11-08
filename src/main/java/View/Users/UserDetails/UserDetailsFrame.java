package View.Users.UserDetails;

import View.CommonLibs.PopupMenuForMapTables;
import Controller.OptiCxAPIController;
import Model.DataModels.Sites.Phone;
import Model.DataModels.Users.CreateUserRequest;
import Model.DataModels.Users.EnumUserRoles;
import Model.DataModels.Users.NotificationSetting;
import Model.DataModels.Users.NotificationSettingList;
import Model.DataModels.Users.RoleItem;
import Model.DataModels.Users.UserInfo;
import Model.PropertyChangeNames;
import View.Users.AddOrUpdateEnum;
import View.Users.UserDetails.Notifications.NotificationsTableCellRenderer;
import View.Users.UserDetails.Notifications.NotificationsTableModel;
import View.Users.UserDetails.Phones.PhonesTableCellRenderer;
import View.Users.UserDetails.Phones.PhonesTableModel;
import View.Users.UserDetails.Roles.RoleName;
import View.Users.UserDetails.Roles.RolesTableCellEditor;
import View.Users.UserDetails.Roles.RolesTableCellRenderer;
import View.Users.UserDetails.Roles.RolesTableModel;
import View.Users.UserDetails.SpecificUserInfo.SpecificUserInfoFrame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class UserDetailsFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private final OptiCxAPIController controller;
    private UserInfo user;
    private final AddOrUpdateEnum addOrUpdate;

    public UserDetailsFrame(OptiCxAPIController controller, UserInfo user, AddOrUpdateEnum addOrUpdate) {
        initComponents();

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.controller = controller;
        this.user = user;
        this.addOrUpdate = addOrUpdate;

        if (addOrUpdate == AddOrUpdateEnum.ADD) {
            this.jButtonUpdate.setText("Add User");
            this.user = getTemplateUser("");
            fillFields(this.user);
        } else {
            fillFields(this.user);
            this.jButtonUpdate.setText("Update User");
            controller.getNotifications(user.getUserID());
        }
        
        
    }

    private UserInfo getTemplateUser(String username) {

        UserInfo template = new UserInfo();

        template.setExtSFID("");
        template.setUserName(username);
        template.setEmail("");
        template.setFirstName(username);
        template.setLastName(username);

        List<RoleItem> roles = new ArrayList<>();

        template.setRoles(roles);

        return template;

    }

    private void fillFields(UserInfo user) {
        jLabelUserID.setText(user.getUserID());
        jLabelExtSFID.setText(user.getExtSFID());

        DateTimeFormatter edisonFmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateTimeFormatter humanFmt = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
        if (user.getCreatedAt() != null) {
            DateTime stamp = DateTime.parse(user.getCreatedAt(), edisonFmt);
            jLabelCreatedAt.setText(stamp.toString(humanFmt) + " (utc)");
            jLabelCreatedAt.setToolTipText(user.getCreatedAt());
        } else {
            jLabelCreatedAt.setText("?");
            jLabelCreatedAt.setToolTipText("value was null");
        }

        if (user.getModifiedAt() != null) {
            DateTime stamp = DateTime.parse(user.getModifiedAt(), edisonFmt);
            jLabelModifiedAt.setText(stamp.toString(humanFmt) + " (utc)");
            jLabelModifiedAt.setToolTipText(user.getModifiedAt());
        } else {
            jLabelModifiedAt.setText("?");
            jLabelModifiedAt.setToolTipText("value was null");
        }

        jTextFieldUserName.setText(user.getUserName());
        jTextFieldEmail.setText(user.getEmail());
        jTextFieldFirstName.setText(user.getFirstName());
        jTextFieldLastName.setText(user.getLastName());
        jLabelLockedUntil.setText(user.getLockedUntil());
        jLabelPasswordExpires.setText(user.getPassExpires());
        jLabelInitLogin.setText(user.getInitialLogin());
        jTextFieldSFIDInput.setText(user.getExtSFID());
        fillPhonesTable(user);
        fillRolesTable(user);
        
        this.jTableNotifications.setDefaultRenderer(Object.class, new NotificationsTableCellRenderer());
        
        List<NotificationSetting> emptyNotes = new ArrayList<>();
        this.jTableNotifications.setModel(new NotificationsTableModel(emptyNotes));

    }

    private void fillPhonesTable(UserInfo user) {

        if (user.getPhones() == null) {
            user.setPhones(new ArrayList<Phone>());
        }

        this.jTablePhones.setDefaultRenderer(Object.class, new PhonesTableCellRenderer());
        this.jTablePhones.setModel(new PhonesTableModel(user.getPhones()));
    }

    private void fillRolesTable(UserInfo user) {

        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<RoleItem>());
        }

        List<RoleName> roleNames = new ArrayList<>();
        for (EnumUserRoles enumRole : EnumUserRoles.values()) {
            String roleNameStr = enumRole.getEdisonRoleName();
            RoleName roleName = new RoleName(roleNameStr);
            roleNames.add(roleName);
        }

        RolesTableModel tm = new RolesTableModel(user.getRoles(), controller, user, addOrUpdate);
        this.jTableRoles.setModel(tm);
        this.jTableRoles.setDefaultRenderer(RoleName.class, new RolesTableCellRenderer());
        this.jTableRoles.setDefaultEditor(RoleName.class, new RolesTableCellEditor(roleNames));

    }

    private void fillNotificationsTable(List<NotificationSetting> notes) {
        this.jTableNotifications.setDefaultRenderer(Object.class, new NotificationsTableCellRenderer());
        this.jTableNotifications.setModel(new NotificationsTableModel(notes));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        checkbox2 = new java.awt.Checkbox();
        checkbox3 = new java.awt.Checkbox();
        label1 = new java.awt.Label();
        checkbox4 = new java.awt.Checkbox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButtonCancel = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jButtonGetSpecificUserInfo = new javax.swing.JButton();
        jPanelUserReadOnly = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabelCreatedAt = new javax.swing.JLabel();
        jLabelUserID = new javax.swing.JLabel();
        jLabelInitLogin = new javax.swing.JLabel();
        jLabelExtSFID = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelLockedUntil = new javax.swing.JLabel();
        jLabelPasswordExpires = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabelModifiedAt = new javax.swing.JLabel();
        jPanelNameAndEmail = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldLastName = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldUserName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldFirstName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextFieldSFIDInput = new javax.swing.JTextPane();
        jPanelInvitations = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextAreaEmailMessage = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        checkboxEmailInvite = new java.awt.Checkbox();
        jPanelPhones = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablePhones = new javax.swing.JTable();
        jButtonDelPhone = new javax.swing.JButton();
        jButtonAddPhone = new javax.swing.JButton();
        jPanelRoles = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableRoles = new javax.swing.JTable();
        jButtonDelRole = new javax.swing.JButton();
        jButtonAddRole = new javax.swing.JButton();
        jButtonCommitRoleChange = new javax.swing.JButton();
        jPanelNotifications = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableNotifications = new javax.swing.JTable();
        jButtonCommitNotifications = new javax.swing.JButton();
        jButtonDeleteNotification = new javax.swing.JButton();
        jButtonAddNotification = new javax.swing.JButton();

        checkbox2.setLabel("checkbox2");

        checkbox3.setLabel("checkbox3");

        label1.setText("label1");

        checkbox4.setLabel("checkbox4");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("User Details");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonUpdate.setText("Commit");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jButtonGetSpecificUserInfo.setText("GetUserInfo");
        jButtonGetSpecificUserInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGetSpecificUserInfoActionPerformed(evt);
            }
        });

        jPanelUserReadOnly.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Read Only Info"));

        jLabel10.setText("Created At:");

        jLabel11.setText("Modified At:");

        jLabel5.setText("Locked Until:");

        jLabelCreatedAt.setText("*created at*");

        jLabelUserID.setText("*user_id*");

        jLabelInitLogin.setText("*initLogin*");

        jLabelExtSFID.setText("*extsfid*");

        jLabel6.setText("Password Expires:");

        jLabelLockedUntil.setText("*lockedUntil*");

        jLabelPasswordExpires.setText("*passwordExpires*");

        jLabel1.setText("ID:");

        jLabel14.setText("EXT SF ID:");

        jLabel7.setText("Initial Login:");

        jLabelModifiedAt.setText("*modified at*");

        javax.swing.GroupLayout jPanelUserReadOnlyLayout = new javax.swing.GroupLayout(jPanelUserReadOnly);
        jPanelUserReadOnly.setLayout(jPanelUserReadOnlyLayout);
        jPanelUserReadOnlyLayout.setHorizontalGroup(
            jPanelUserReadOnlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUserReadOnlyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelUserReadOnlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelUserReadOnlyLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelUserID))
                    .addGroup(jPanelUserReadOnlyLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelExtSFID))
                    .addGroup(jPanelUserReadOnlyLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelInitLogin))
                    .addGroup(jPanelUserReadOnlyLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelCreatedAt))
                    .addGroup(jPanelUserReadOnlyLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelModifiedAt))
                    .addGroup(jPanelUserReadOnlyLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelLockedUntil))
                    .addGroup(jPanelUserReadOnlyLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelPasswordExpires)))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        jPanelUserReadOnlyLayout.setVerticalGroup(
            jPanelUserReadOnlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUserReadOnlyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelUserReadOnlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelUserID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelUserReadOnlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelExtSFID)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelUserReadOnlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabelInitLogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelUserReadOnlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabelCreatedAt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelUserReadOnlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabelModifiedAt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelUserReadOnlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabelLockedUntil))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelUserReadOnlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabelPasswordExpires))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelNameAndEmail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Name and Email"));

        jLabel3.setText("First Name:");

        jTextFieldLastName.setText("jTextField2");

        jLabel12.setText("Email:");

        jTextFieldUserName.setText("jTextField2");
        jTextFieldUserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldUserNameActionPerformed(evt);
            }
        });

        jLabel4.setText("Last Name:");

        jTextFieldEmail.setText("jTextField2");

        jLabel2.setText("UserName:");

        jTextFieldFirstName.setText("jTextField1");

        jLabel8.setText("SFID (Optional - Portal Only):");

        jScrollPane7.setViewportView(jTextPane1);

        jScrollPane8.setViewportView(jTextFieldSFIDInput);

        javax.swing.GroupLayout jPanelNameAndEmailLayout = new javax.swing.GroupLayout(jPanelNameAndEmail);
        jPanelNameAndEmail.setLayout(jPanelNameAndEmailLayout);
        jPanelNameAndEmailLayout.setHorizontalGroup(
            jPanelNameAndEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNameAndEmailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNameAndEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNameAndEmailLayout.createSequentialGroup()
                        .addGroup(jPanelNameAndEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel12)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelNameAndEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                            .addGroup(jPanelNameAndEmailLayout.createSequentialGroup()
                                .addComponent(jTextFieldUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                .addGap(121, 121, 121))
                            .addGroup(jPanelNameAndEmailLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanelNameAndEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldLastName, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextFieldFirstName))))
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64))
                    .addGroup(jPanelNameAndEmailLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanelNameAndEmailLayout.setVerticalGroup(
            jPanelNameAndEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNameAndEmailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNameAndEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNameAndEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelNameAndEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNameAndEmailLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelNameAndEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelNameAndEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelNameAndEmailLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)))
                .addGroup(jPanelNameAndEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)))
        );

        jPanelInvitations.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Invitations"));

        jTextAreaEmailMessage.setColumns(20);
        jTextAreaEmailMessage.setRows(5);
        jScrollPane5.setViewportView(jTextAreaEmailMessage);

        jLabel15.setText("Invite Message:");

        checkboxEmailInvite.setLabel("Email Invitation?");

        javax.swing.GroupLayout jPanelInvitationsLayout = new javax.swing.GroupLayout(jPanelInvitations);
        jPanelInvitations.setLayout(jPanelInvitationsLayout);
        jPanelInvitationsLayout.setHorizontalGroup(
            jPanelInvitationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInvitationsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelInvitationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
                    .addGroup(jPanelInvitationsLayout.createSequentialGroup()
                        .addGroup(jPanelInvitationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkboxEmailInvite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelInvitationsLayout.setVerticalGroup(
            jPanelInvitationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInvitationsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkboxEmailInvite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelPhones.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Phones"));

        jTablePhones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTablePhones.setCellSelectionEnabled(true);
        jTablePhones.setPreferredSize(new java.awt.Dimension(250, 64));
        jTablePhones.setShowGrid(true);
        jTablePhones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTablePhonesMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTablePhones);

        jButtonDelPhone.setText("Del Phone");
        jButtonDelPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDelPhoneActionPerformed(evt);
            }
        });

        jButtonAddPhone.setText("Add Phone");
        jButtonAddPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddPhoneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelPhonesLayout = new javax.swing.GroupLayout(jPanelPhones);
        jPanelPhones.setLayout(jPanelPhonesLayout);
        jPanelPhonesLayout.setHorizontalGroup(
            jPanelPhonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPhonesLayout.createSequentialGroup()
                .addGroup(jPanelPhonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPhonesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanelPhonesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonAddPhone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDelPhone)))
                .addContainerGap())
        );
        jPanelPhonesLayout.setVerticalGroup(
            jPanelPhonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPhonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPhonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonDelPhone)
                    .addComponent(jButtonAddPhone))
                .addContainerGap())
        );

        jPanelRoles.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Roles"));

        jTableRoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableRoles.setShowGrid(true);
        jTableRoles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableRolesMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(jTableRoles);

        jButtonDelRole.setText("Del Role");
        jButtonDelRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDelRoleActionPerformed(evt);
            }
        });

        jButtonAddRole.setText("Add Role");
        jButtonAddRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddRoleActionPerformed(evt);
            }
        });

        jButtonCommitRoleChange.setText("Commit Change");
        jButtonCommitRoleChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCommitRoleChangeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelRolesLayout = new javax.swing.GroupLayout(jPanelRoles);
        jPanelRoles.setLayout(jPanelRolesLayout);
        jPanelRolesLayout.setHorizontalGroup(
            jPanelRolesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRolesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRolesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRolesLayout.createSequentialGroup()
                        .addGap(0, 346, Short.MAX_VALUE)
                        .addComponent(jButtonCommitRoleChange)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAddRole)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDelRole))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelRolesLayout.setVerticalGroup(
            jPanelRolesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRolesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelRolesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonDelRole)
                    .addComponent(jButtonAddRole)
                    .addComponent(jButtonCommitRoleChange))
                .addContainerGap())
        );

        jPanelNotifications.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "User Notifications"));

        jTableNotifications.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableNotifications);

        jButtonCommitNotifications.setText("Commit Notes");
        jButtonCommitNotifications.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCommitNotificationsActionPerformed(evt);
            }
        });

        jButtonDeleteNotification.setText("Delete");
        jButtonDeleteNotification.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteNotificationActionPerformed(evt);
            }
        });

        jButtonAddNotification.setText("Add");
        jButtonAddNotification.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddNotificationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelNotificationsLayout = new javax.swing.GroupLayout(jPanelNotifications);
        jPanelNotifications.setLayout(jPanelNotificationsLayout);
        jPanelNotificationsLayout.setHorizontalGroup(
            jPanelNotificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNotificationsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNotificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelNotificationsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonAddNotification)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDeleteNotification)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCommitNotifications)))
                .addContainerGap())
        );
        jPanelNotificationsLayout.setVerticalGroup(
            jPanelNotificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNotificationsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelNotificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCommitNotifications)
                    .addComponent(jButtonDeleteNotification)
                    .addComponent(jButtonAddNotification))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelInvitations, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonGetSpecificUserInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanelPhones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelUserReadOnly, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelNameAndEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelRoles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanelNotifications, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelNameAndEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelUserReadOnly, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelRoles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelPhones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelInvitations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelNotifications, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonUpdate)
                    .addComponent(jButtonGetSpecificUserInfo))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        user.setUserName(jTextFieldUserName.getText());
        user.setEmail(jTextFieldEmail.getText());
        user.setFirstName(jTextFieldFirstName.getText());
        user.setLastName(jTextFieldLastName.getText());
        user.setExtSFID(jTextFieldSFIDInput.getText());
        
        //user.setPhones(((MapTableModel) this.jTablePhones.getModel()).getTableAsMap());
        //user.setRoles(((MapTableModel) this.jTableRoles.getModel()).getTableAsMap());

        if (addOrUpdate == AddOrUpdateEnum.UPDATE) {
            controller.putUserInfo(user);
        } else {
            user.setExtSFID( jTextFieldSFIDInput.getText());
            Boolean sendEmail = this.checkboxEmailInvite.getState();
            String message = this.jTextAreaEmailMessage.getText();
            CreateUserRequest cur = new CreateUserRequest(user, "You are invited", !sendEmail);
            controller.createUser(cur);
        }
        controller.removePropChangeListener(this);
        this.dispose();
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonGetSpecificUserInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetSpecificUserInfoActionPerformed

        SpecificUserInfoFrame frame = new SpecificUserInfoFrame(controller);
        controller.addModelListener(frame);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);

        controller.queryForSpecificUserInfo(user.getUserID());
    }//GEN-LAST:event_jButtonGetSpecificUserInfoActionPerformed

    private void jTablePhonesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePhonesMousePressed
        if (evt.isPopupTrigger()) {
            PopupMenuForMapTables popup = new PopupMenuForMapTables(evt, jTablePhones);
        }
    }//GEN-LAST:event_jTablePhonesMousePressed

    private void jTableRolesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRolesMousePressed
        if (evt.isPopupTrigger()) {
            PopupMenuForMapTables popup = new PopupMenuForMapTables(evt, jTableRoles);
        }
    }//GEN-LAST:event_jTableRolesMousePressed

    private void jButtonDelRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDelRoleActionPerformed

        if (jTableRoles.getSelectedRowCount() > 0) {
            int row = jTableRoles.getSelectedRow();
            int modelRow = jTableRoles.convertRowIndexToModel(row);
            RoleItem roleToDelete = user.getRoles().get(modelRow);
            if (addOrUpdate == AddOrUpdateEnum.UPDATE) {
                controller.deleteUserRole(user.getUserID(), roleToDelete.getSid());
            } else {
                RolesTableModel rolesTableModel = (RolesTableModel) jTableRoles.getModel();
                rolesTableModel.deleteRole(roleToDelete);
            }
        }
    }//GEN-LAST:event_jButtonDelRoleActionPerformed

    private void jButtonDelPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDelPhoneActionPerformed
        if (jTablePhones.getSelectedRowCount() > 0) {
            int row = jTablePhones.getSelectedRow();
            int modelRow = jTablePhones.convertRowIndexToModel(row);
            PhonesTableModel mod = (PhonesTableModel) jTablePhones.getModel();
            mod.deletePhone(modelRow);
        }
    }//GEN-LAST:event_jButtonDelPhoneActionPerformed

    private void jButtonAddRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddRoleActionPerformed
        RolesTableModel rolesTableModel = (RolesTableModel) jTableRoles.getModel();
        rolesTableModel.addBlankRole();
    }//GEN-LAST:event_jButtonAddRoleActionPerformed

    private void jButtonCommitRoleChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCommitRoleChangeActionPerformed

        if (jTableRoles.getSelectedRowCount() > 0 && addOrUpdate == AddOrUpdateEnum.UPDATE) {
            int row = jTableRoles.getSelectedRow();
            int modelRow = jTableRoles.convertRowIndexToModel(row);
            RolesTableModel roleModel = (RolesTableModel) jTableRoles.getModel();
            RoleItem roleToUpdate = roleModel.getRoleItem(modelRow);
            List<RoleItem> roleItems = new ArrayList<>();
            roleItems.add(roleToUpdate);
            controller.changeRolesForSid(user.getUserID(), roleToUpdate.getSid(), roleItems);
        }
    }//GEN-LAST:event_jButtonCommitRoleChangeActionPerformed

    private void jButtonAddPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddPhoneActionPerformed
        PhonesTableModel mod = (PhonesTableModel) jTablePhones.getModel();
        mod.addBlankPhone();
    }//GEN-LAST:event_jButtonAddPhoneActionPerformed

    private void jButtonAddNotificationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddNotificationActionPerformed
        NotificationsTableModel mod = (NotificationsTableModel) jTableNotifications.getModel();
        mod.addBlankNote();
    }//GEN-LAST:event_jButtonAddNotificationActionPerformed

    private void jButtonDeleteNotificationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteNotificationActionPerformed
        if (jTableNotifications.getSelectedRowCount() > 0) {
            int rowNumber = jTableNotifications.getSelectedRow();
            int modelIndex = jTableNotifications.convertRowIndexToModel(rowNumber);
            NotificationsTableModel mod = (NotificationsTableModel) jTableNotifications.getModel();
            mod.deleteNotification(modelIndex);
        }
    }//GEN-LAST:event_jButtonDeleteNotificationActionPerformed

    private void jButtonCommitNotificationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCommitNotificationsActionPerformed
        
        NotificationsTableModel mod = (NotificationsTableModel)(jTableNotifications.getModel());
        List<NotificationSetting> notifications = mod.getNotifications();
        
        for( NotificationSetting note : notifications  ){
            note.setTargetType("email"); //or sms
            note.setJobType("realTime"); //or daily
        }
        
        NotificationSettingList req = new NotificationSettingList();
        req.setNotifications(notifications);
        
        controller.setNotifications(user.getUserID(), req);
                     
    }//GEN-LAST:event_jButtonCommitNotificationsActionPerformed

    private void jTextFieldUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldUserNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldUserNameActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Checkbox checkbox2;
    private java.awt.Checkbox checkbox3;
    private java.awt.Checkbox checkbox4;
    private java.awt.Checkbox checkboxEmailInvite;
    private javax.swing.JButton jButtonAddNotification;
    private javax.swing.JButton jButtonAddPhone;
    private javax.swing.JButton jButtonAddRole;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonCommitNotifications;
    private javax.swing.JButton jButtonCommitRoleChange;
    private javax.swing.JButton jButtonDelPhone;
    private javax.swing.JButton jButtonDelRole;
    private javax.swing.JButton jButtonDeleteNotification;
    private javax.swing.JButton jButtonGetSpecificUserInfo;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelCreatedAt;
    private javax.swing.JLabel jLabelExtSFID;
    private javax.swing.JLabel jLabelInitLogin;
    private javax.swing.JLabel jLabelLockedUntil;
    private javax.swing.JLabel jLabelModifiedAt;
    private javax.swing.JLabel jLabelPasswordExpires;
    private javax.swing.JLabel jLabelUserID;
    private javax.swing.JPanel jPanelInvitations;
    private javax.swing.JPanel jPanelNameAndEmail;
    private javax.swing.JPanel jPanelNotifications;
    private javax.swing.JPanel jPanelPhones;
    private javax.swing.JPanel jPanelRoles;
    private javax.swing.JPanel jPanelUserReadOnly;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTableNotifications;
    private javax.swing.JTable jTablePhones;
    private javax.swing.JTable jTableRoles;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextAreaEmailMessage;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldFirstName;
    private javax.swing.JTextField jTextFieldLastName;
    private javax.swing.JTextPane jTextFieldSFIDInput;
    private javax.swing.JTextField jTextFieldUserName;
    private javax.swing.JTextPane jTextPane1;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.UserUpdated.getName())) {
            user = (UserInfo) evt.getNewValue();
            fillFields(user);
        }

        if (propName.equals(PropertyChangeNames.SpecificUserInfoRetrieved.getName())) {
            user = (UserInfo) evt.getNewValue();
            fillFields(user);
        }

        if (propName.equals(PropertyChangeNames.UserRoleDeleted.getName())) {
            controller.queryForSpecificUserInfo(user.getUserID());
        }

        if (propName.equals(PropertyChangeNames.UserRolesChanged.getName())) {
            controller.queryForSpecificUserInfo(user.getUserID());
        }

        if (propName.equals(PropertyChangeNames.UserNotificatonsRetrieved.getName())) {
            //NotificationSettingList notes = (NotificationSettingList) evt.getNewValue();
            List<NotificationSetting> notes = (List<NotificationSetting>) evt.getNewValue();
            fillNotificationsTable(notes);
        }

        if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            controller.removePropChangeListener(this);
            this.dispose();
        }
    }
}
