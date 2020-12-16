package bubbleindex;

import bubbleindex.noGUI.RunType;
import com.nativelibs4java.util.IOUtils;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

/**
 * GUI Creates, draws, and contains the input fields to run
 * the GUI mode of The Bubble Index application.
 * 
 * @author thebubbleindex
 */
public class GUI extends javax.swing.JFrame {

    private double omega;
    private double mCoeff;
    private double tCrit;
    private String windowsInput;
    private String categoryName;
    private String selectionName;
    private boolean GRAPH_ON;
    private boolean isCustomRange;
    private Date begDate;
    private Date endDate;
    private String quandlKey;
    
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        final Properties guiProperties = new Properties();
        InputStream input = null;
        try {
            Logs.myLogger.info("Reading gui.properties.");
            input = new FileInputStream(Indices.getFilePath() + 
                    System.getProperty("file.separator") + "ProgramData" + 
                    System.getProperty("file.separator") + "gui.properties");
            guiProperties.load(input);
            customInit(guiProperties);

        } catch (final FileNotFoundException ex) {
            input = null;
            Logs.myLogger.error("Could not find gui.properties file. {}", ex);
        } catch (final IOException ex) {
            input = null;
            Logs.myLogger.error("Error while reading gui.properties file. {}", ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (final IOException ex) {
                    Utilities.displayOutput("Error closing gui.properties file. " + ex.getLocalizedMessage(), false);
                }
            }
        }
                
        try {
            Utilities.displayOutput("Working Dir: " + Indices.getFilePath(), false);
        } catch (final UnsupportedEncodingException ex) {
            Utilities.displayOutput("Error finding working dir..." + ex.getLocalizedMessage(), false);
        }
    }
    
    /**
     * Creates new form GUI
     * @param omega
     * @param mCoeff
     * @param tCrit
     */
    public GUI(final double omega, final double tCrit, final double mCoeff) {
        initComponents();
        final Properties guiProperties = new Properties();
        InputStream input = null;
        try {
            Logs.myLogger.info("Reading gui.properties.");
            input = new FileInputStream(Indices.getFilePath() + 
                    System.getProperty("file.separator") + "ProgramData" + 
                    System.getProperty("file.separator") + "gui.properties");
            guiProperties.load(input);
            customInit(guiProperties);

        } catch (final FileNotFoundException ex) {
            input = null;
            Logs.myLogger.error("Could not find gui.properties file. {}", ex);
        } catch (final IOException ex) {
            input = null;
            Logs.myLogger.error("Error while reading gui.properties file. {}", ex);
        } 
                
        OmegaTextField.setText(String.valueOf(omega));
        TCriticalField.setText(String.valueOf(tCrit));
        MTextField.setText(String.valueOf(mCoeff));
        
        try {
            Utilities.displayOutput("Working Dir: " + Indices.getFilePath(), false);
        } catch (final UnsupportedEncodingException ex) {
            Utilities.displayOutput("Error finding working dir..." + ex.getLocalizedMessage(), false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        quandlKeyTextField = new javax.swing.JTextField();
        quandlKeyLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("The Bubble Index");

        TitleLabel.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        TitleLabel.setText("The Bubble Index\u2122");
        TitleLabel.setToolTipText("");

        RunProgram.setText("Run");
        RunProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunProgramActionPerformed(evt);
            }
        });

        OutputText.setRows(100);
        OutputText.setFont(new Font("Courier New", Font.PLAIN, 12));

        ExitButton.setText("Exit");
        ExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButtonActionPerformed(evt);
            }
        });

        DropDownCategory.setModel(new javax.swing.DefaultComboBoxModel(Indices.getCategoriesAsArray()));
        DropDownCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DropDownCategoryActionPerformed(evt);
            }
        });

        DropDownSelection.setModel(new javax.swing.DefaultComboBoxModel(Indices.categoriesAndComponents.get((String)DropDownCategory.getSelectedItem()).getComponentsAsArray()));

        CategoryLabel.setText("Type");

        SelectionLabel.setText("Name");

        SelectionAreaLabel.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        SelectionAreaLabel.setText("Choose Index:");

        ModelOptionsHeaderLabel.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        ModelOptionsHeaderLabel.setText("Model Options:");

        StopRunningButton.setText("Stop");
        StopRunningButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopRunningButtonActionPerformed(evt);
            }
        });

        GraphCheckBox.setText("Plot Graph");

        customBegDate.setValue(new Date(0));

        customEndDate.setValue(new Date());

        customDates.setText("Plot Custom Dates");

        GraphOptionsHeaderLabel.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        GraphOptionsHeaderLabel.setText("Graph Options:");

        updateDataButton.setText("Update Data");
        updateDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateDataButtonActionPerformed(evt);
            }
        });

        runAllNames.setText("Run All Names");
        runAllNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runAllNamesActionPerformed(evt);
            }
        });

        runAllTypes.setText("Run All Types");
        runAllTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runAllTypesActionPerformed(evt);
            }
        });

        threadsTextLabel.setText("Threads");

        ThreadNumber.setText("4");

        windowsTextField.setText("52, 104, 153, 256, 512, 1260, 1764");

        WindowsLabel.setText("Windows:");

        OmegaTextLabel.setText("Omega");

        MTextLabel.setText("M");

        OmegaTextField.setText("6.28319");

        MTextField.setText("0.38");

        TCriticalLabel.setText("T_critical");

        TCriticalField.setText("21.0");

        forceCPUBox.setText("Force CPU");

        quandlKeyLabel.setText("Quandl API Key");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(OutputText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(289, 289, 289))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 137, Short.MAX_VALUE)
                        .addComponent(updateDataButton)
                        .addGap(51, 51, 51)
                        .addComponent(runAllTypes)
                        .addGap(53, 53, 53)
                        .addComponent(runAllNames)
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RunProgram, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(StopRunningButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ExitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(customDates)
                                            .addComponent(GraphCheckBox))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(customBegDate)
                                            .addComponent(customEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(GraphOptionsHeaderLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(quandlKeyLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(quandlKeyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(WindowsLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(windowsTextField))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DropDownCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SelectionLabel)
                                    .addComponent(DropDownSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CategoryLabel)
                                    .addComponent(SelectionAreaLabel)
                                    .addComponent(ModelOptionsHeaderLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(MTextLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(OmegaTextLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(threadsTextLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(TCriticalLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(TCriticalField)
                                            .addComponent(ThreadNumber)
                                            .addComponent(MTextField)
                                            .addComponent(OmegaTextField)))
                                    .addComponent(forceCPUBox, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(73, 73, 73))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(TitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(SelectionAreaLabel)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CategoryLabel)
                            .addComponent(threadsTextLabel)
                            .addComponent(ThreadNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(DropDownCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TCriticalLabel)
                                    .addComponent(TCriticalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addComponent(SelectionLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DropDownSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(OmegaTextLabel)
                                    .addComponent(OmegaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(MTextLabel)
                                    .addComponent(MTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(ModelOptionsHeaderLabel))
                    .addComponent(forceCPUBox))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(windowsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(WindowsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(quandlKeyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(quandlKeyLabel))
                    .addComponent(GraphOptionsHeaderLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(GraphCheckBox)
                            .addComponent(customBegDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customDates))
                    .addComponent(customEndDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RunProgram, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StopRunningButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ExitButton)
                    .addComponent(runAllNames)
                    .addComponent(runAllTypes)
                    .addComponent(updateDataButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OutputText, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * RunProgramActionPerformed starts when the Run button is pressed. 
     * Creates a thread to run The Bubble Index for a single selection name
     * which is parsed from the drop down menus.
     * <p>
     * If the Graph check box is selected, then the plots are called upon
     * completion of the Run. The plots are designed to only plot the first
     * four windows of the windowsInput text field.
     * 
     * @param evt The click action event
     */
    private void RunProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RunProgramActionPerformed
        initializeVariables();
        runnableGUI();
        
        final BubbleIndexWorker bubbleIndexWorker = new BubbleIndexWorker(RunType.Single, this, windowsInput, omega,
            mCoeff, tCrit, categoryName, selectionName,
            begDate, endDate, isCustomRange, GRAPH_ON);
        bubbleIndexWorker.execute();
    }//GEN-LAST:event_RunProgramActionPerformed
    
    /**
     * initializeVariables gets values from GUI in preparation for any
     * of the Runs.
     * 
     */
    public void initializeVariables() {   
        DailyDataCache.reset();
        RunContext.threadNumber = Integer.parseInt(ThreadNumber.getText().trim());
        RunContext.Stop = false;
        GRAPH_ON = GraphCheckBox.isSelected();
        isCustomRange = customDates.isSelected();
        categoryName = (String)DropDownCategory.getSelectedItem();
        selectionName = (String)DropDownSelection.getSelectedItem();
        omega = Double.parseDouble(OmegaTextField.getText().trim());
        mCoeff = Double.parseDouble(MTextField.getText().trim());
        tCrit = Double.parseDouble(TCriticalField.getText().trim());
        windowsInput = windowsTextField.getText();
        try {
            quandlKey = quandlKeyTextField.getText();
            Logs.myLogger.info("Quandl Key provided: {}", quandlKey);
        } catch (final Exception ex) {
            quandlKey = "";
            Logs.myLogger.info("No Quandl Key provided.");
        }
        if (customDates.isSelected()){
            begDate = (Date)customBegDate.getValue();
            endDate = (Date)customEndDate.getValue();
        }
        else {
            begDate = new Date(0);
            endDate = new Date();
        }
    }

    /**
     * runnableGUI disable buttons during program operation
     */
    public void runnableGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { 
                RunProgram.setEnabled(false);  
                runAllNames.setEnabled(false);
                runAllTypes.setEnabled(false);
                StopRunningButton.setEnabled(true);
                updateDataButton.setEnabled(false);
            }
        });
    }
    
    /**
     * resetGUI enable buttons after application finishes
     */
    public void resetGUI() {     
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { 
                RunProgram.setEnabled(true);
                runAllNames.setEnabled(true);
                runAllTypes.setEnabled(true);
                StopRunningButton.setEnabled(false);
                customEndDate.setValue(new Date());
                updateDataButton.setEnabled(true);
            } 
        });
    }
    
    /**
     * ExitButtonActionPerformed Calls System.exit(0) upon clicking.
     * 
     * @param evt The click action event
     */
    private void ExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitButtonActionPerformed
        Logs.myLogger.info("Exit button clicked");
        System.exit(0);
    }//GEN-LAST:event_ExitButtonActionPerformed

    /**
     * DropDownCategoryActionPerformed Updates the drop down boxes upon interaction click event.
     * 
     * @param evt The click action event
     */
    private void DropDownCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DropDownCategoryActionPerformed
        final JComboBox cb = (JComboBox)evt.getSource();
        updateDropDownSelection((String)cb.getSelectedItem()); 
    }//GEN-LAST:event_DropDownCategoryActionPerformed

    /**
     * StopRunningButtonActionPerformed Changes the Stop variable in RunContext to true.
     * This should initiate a complete halt of any executing
     * process.
     * 
     * @param evt The click action event
     */
    private void StopRunningButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopRunningButtonActionPerformed
        Logs.myLogger.info("Stop button clicked");
        RunContext.Stop = true;
        DailyDataCache.reset();
    }//GEN-LAST:event_StopRunningButtonActionPerformed
    
    /**
     * runAllNamesActionPerformed Creates a single thread which loops through
     * all Selection Names in a single category. This thread in turn
     * forms a BubbleIndexThread instance which Runs a single Selection Name.
     * 
     * @param evt The click action event.
     */
    private void runAllNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runAllNamesActionPerformed

        initializeVariables();
        runnableGUI();
        
        final BubbleIndexWorker bubbleIndexWorker = new BubbleIndexWorker(RunType.Category, this, windowsInput, omega,
            mCoeff, tCrit, categoryName, selectionName,
            begDate, endDate, isCustomRange, GRAPH_ON);
        bubbleIndexWorker.execute();
    }//GEN-LAST:event_runAllNamesActionPerformed

    /**
     * updateDataButtonActionPerformed Creates a thread which loops through
     * all Categories specified in the UpdateCategories.csv external file. For
     * every category it loops through all Selection Names obtained from
     * the corresponding category's folder file: UpdateSelection.csv.
     * <p>
     * Based on this file, it fetches the appropriate URL containing the daily
     * price data.
     * 
     * @param evt The click action event
     */
    private void updateDataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateDataButtonActionPerformed
        initializeVariables();
        runnableGUI();
        
        final UpdateWorker updateWorker = new UpdateWorker(this, quandlKey);
        
        updateWorker.execute();
    }//GEN-LAST:event_updateDataButtonActionPerformed

    /**
     * runAllTypesActionPerformed Creates a thread which loops through all
     * Categories and all Selection Names. This thread in turn
     * forms a BubbleIndexThread instance which Runs a single Selection Name.
     * 
     * @param evt The click action event
     */
    private void runAllTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runAllTypesActionPerformed
        initializeVariables();
        runnableGUI();
        
        final BubbleIndexWorker bubbleIndexWorker = new BubbleIndexWorker(RunType.All, this, windowsInput, omega,
            mCoeff, tCrit, categoryName, selectionName,
            begDate, endDate, isCustomRange, GRAPH_ON);
        bubbleIndexWorker.execute();
  
    }//GEN-LAST:event_runAllTypesActionPerformed

    /**
     * updateDropDownSelection Grabs the appropriate list to fill the drop
     * down Selection Name box.
     * 
     * @param name The name of the category
     */
    protected void updateDropDownSelection(final String name) {

        for (final String category : Indices.categoriesAndComponents.keySet()) {
            if (name.equals(category)) {
                final InputCategory inputCategory = Indices.categoriesAndComponents.get(category);
                DropDownCategory.setSelectedItem(name);
                DropDownSelection.setModel(new javax.swing.DefaultComboBoxModel(inputCategory.getComponentsAsArray()));
            }
        }
    }

    /**
     * GUImain provides the main entrance to create and initialize the GUI
     * containers.
     * 
     */
    public static void GUImain() {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /* Create and display the form */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        }
        //</editor-fold>
        
        Logs.myLogger.info("Initializing GUI. Loading categories.");
        Indices.initialize();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI().setVisible(true);
                                    
                try {
                    Logs.myLogger.info("Reading OpenCL source file.");
                    RunIndex.src = IOUtils.readText(RunIndex.class.getResource("GPUKernel.cl"));
                } catch (final IOException ex) {
                    Logs.myLogger.error("IOException Exception. Failed to read OpenCL source file. {}", ex);
                    Utilities.displayOutput("Error. OpenCL file missing.", false);
                }

            }
        });
    }
    
    /**
     * GUImain provides the main entrance to create and initialize the GUI
     * containers.
     * 
     * @param omega
     * @param tCrit
     * @param mCoeff
     */
    public static void GUImain(final double omega, final double tCrit, final double mCoeff) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /* Create and display the form */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        }
        //</editor-fold>
        Logs.myLogger.info("Initializing GUI. Loading categories");
        Indices.initialize();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI(omega, tCrit, mCoeff).setVisible(true);
                                    
                try {
                    Logs.myLogger.info("Reading OpenCL source file.");
                    RunIndex.src = IOUtils.readText(RunIndex.class.getResource("GPUKernel.cl"));
                } catch (final IOException ex) {
                    Logs.myLogger.error("IOException Exception. Failed to read OpenCL source file. {}", ex);
                    Utilities.displayOutput("Error. OpenCL file missing.", false);
                }

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private final javax.swing.JLabel CategoryLabel = new javax.swing.JLabel();
    private final javax.swing.JComboBox DropDownCategory = new javax.swing.JComboBox();
    private final javax.swing.JComboBox DropDownSelection = new javax.swing.JComboBox();
    private final javax.swing.JButton ExitButton = new javax.swing.JButton();
    private final javax.swing.JCheckBox GraphCheckBox = new javax.swing.JCheckBox();
    private final javax.swing.JLabel GraphOptionsHeaderLabel = new javax.swing.JLabel();
    public final javax.swing.JTextField MTextField = new javax.swing.JTextField();
    private final javax.swing.JLabel MTextLabel = new javax.swing.JLabel();
    private final javax.swing.JLabel ModelOptionsHeaderLabel = new javax.swing.JLabel();
    private final javax.swing.JTextField OmegaTextField = new javax.swing.JTextField();
    private final javax.swing.JLabel OmegaTextLabel = new javax.swing.JLabel();
    public static final java.awt.TextArea OutputText = new java.awt.TextArea();
    private final javax.swing.JButton RunProgram = new javax.swing.JButton();
    private final javax.swing.JLabel SelectionAreaLabel = new javax.swing.JLabel();
    private final javax.swing.JLabel SelectionLabel = new javax.swing.JLabel();
    private final javax.swing.JButton StopRunningButton = new javax.swing.JButton();
    public final javax.swing.JTextField TCriticalField = new javax.swing.JTextField();
    private final javax.swing.JLabel TCriticalLabel = new javax.swing.JLabel();
    public final javax.swing.JTextField ThreadNumber = new javax.swing.JTextField();
    private final javax.swing.JLabel TitleLabel = new javax.swing.JLabel();
    private final javax.swing.JLabel WindowsLabel = new javax.swing.JLabel();
    private final javax.swing.JFormattedTextField customBegDate = new javax.swing.JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
    private final javax.swing.JCheckBox customDates = new javax.swing.JCheckBox();
    private final javax.swing.JFormattedTextField customEndDate = new javax.swing.JFormattedTextField(new SimpleDateFormat ("yyyy-MM-dd"));
    public final javax.swing.JCheckBox forceCPUBox = new javax.swing.JCheckBox();
    private javax.swing.JLabel quandlKeyLabel;
    private javax.swing.JTextField quandlKeyTextField;
    private final javax.swing.JButton runAllNames = new javax.swing.JButton();
    private final javax.swing.JButton runAllTypes = new javax.swing.JButton();
    private final javax.swing.JLabel threadsTextLabel = new javax.swing.JLabel();
    private final javax.swing.JButton updateDataButton = new javax.swing.JButton();
    public final javax.swing.JTextField windowsTextField = new javax.swing.JTextField();
    // End of variables declaration//GEN-END:variables

    private void customInit(final Properties guiProperties) {

        OmegaTextField.setText(guiProperties.getProperty("omega").trim());
        TCriticalField.setText(guiProperties.getProperty("tcrit").trim());
        MTextField.setText(guiProperties.getProperty("mcoeff").trim());
        windowsTextField.setText(guiProperties.getProperty("windows").trim());
        ThreadNumber.setText(guiProperties.getProperty("threads"));
        customBegDate.setText(guiProperties.getProperty("begdate"));
        customEndDate.setText(guiProperties.getProperty("enddate"));
        
    }
}
