package bubbleindex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
/**
 *
 * @author thebubbleindex
 */
public class BubbleIndexPlot {
    private final int[] backtestDayLengths;
    private final long serialVersionUID = 1L;
    public final Date begDate;
    public final Date endDate;
    public final boolean isCustomRange;

    /** MouseEvent X & Y. */
    private int m_iX, m_iY; private double m_dX, m_dY, m_dXX, m_dYY;
    private double index4Value = 0.0;
    private double[] indexValue = {0.0, 0.0, 0.0, 0.0};
    /** Crosshair X & Y. */
    private Line2D m_l2dCrosshairX, m_l2dCrosshairY;
    public final ChartPanel chartPanel;
    private final String selectionName;
    private final List<String> dailyPriceData;
    private final List<String> dailyPriceDate;
    private final String categoryName;
    
    static {
        // set a theme using the new shadow generator feature available in
        // 1.0.14 - for backwards compatibility it is not enabled by default
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow",
                true));
    }

    public BubbleIndexPlot(final String categoryName, final String selectionName, final String windowsString,
            final Date begDate, final Date endDate, final boolean isCustomRange, final List<String> dailyPriceData,
            final List<String> dailyPriceDate) {
        Logs.myLogger.info("Initializing Bubble Index Plot.");
        this.categoryName = categoryName;
        this.selectionName = selectionName;
        this.begDate = begDate;
        this.endDate = endDate;
        this.isCustomRange = isCustomRange;
        this.dailyPriceData = dailyPriceData;
        this.dailyPriceDate = dailyPriceDate;
        final String windowsStringArray[] = windowsString.split(",");
        backtestDayLengths = new int[windowsStringArray.length];
        int i = 0;
        for (final String window : windowsStringArray) {
            backtestDayLengths[i++] = Integer.parseInt(window);
        }
                    
        final String title = "The Bubble Index\u2122: " + this.selectionName;
        chartPanel = createChart();
        EventQueue.invokeLater(new Runnable() {            
            @Override
            public void run() {
                drawBubbleIndexPlot(title);          
            }
        });
    }
    
    /**
     * 
     * @param title 
     */
    public void drawBubbleIndexPlot(final String title) {

        final JFrame f = new JFrame(title);
        f.setTitle(title);
        f.setLayout(new BorderLayout(0,5));
        f.add(chartPanel, BorderLayout.CENTER);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setHorizontalAxisTrace(true);
        chartPanel.setVerticalAxisTrace(true);
        chartPanel.addChartMouseListener(new ChartMouseListener() {

            @Override
            public void chartMouseClicked(final ChartMouseEvent event){
                //Do nothing
            }

            @Override
            public void chartMouseMoved(final ChartMouseEvent e) {
                int iX = e.getTrigger().getX();

                m_iX = iX;
                m_dX = (double)iX;
                drawRTInfo();    //Draw Realtime tooltip(info) - Can run alone
            } 
        });

        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        f.add(panel, BorderLayout.SOUTH);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                final int result = JOptionPane.showConfirmDialog(
                        f, "Are you sure?");
                if (result == JOptionPane.OK_OPTION) {
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setVisible(false);
                    f.dispose();
                }
            }
        });        
        
        f.setVisible(true);
    }
    
    /**
     * Creates a chart.
     *
     * @param dataset  a dataset.
     *
     * @return A chart.
     */
    private ChartPanel createChart() {
        final XYDataset dataset = createDataset();

        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "The Bubble Index\u2122: " + selectionName, // title
            "Date",                         // x-axis label
            "Index Value (Relative)",       // y-axis label
            dataset,                        // data
            true,                           // create legend?
            false,                           // generate tooltips?
            false                           // generate URLs?
        );    
            
        chart.setBackgroundPaint(Color.white);
        

        final XYPlot plot = (XYPlot) chart.getPlot();
        final NumberAxis axis2 = new NumberAxis("Price");
        axis2.setAutoRangeIncludesZero(false);
        plot.setRangeAxis(1, axis2);
        plot.setDataset(1, createDataset2());
        plot.mapDatasetToRangeAxis(1,1);
        plot.setBackgroundPaint(Color.BLACK);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        
        final XYItemRenderer r = plot.getRenderer();        
        if (r instanceof XYLineAndShapeRenderer) {
            final XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(false);
            renderer.setBaseShapesFilled(false);
            renderer.setDrawSeriesLineAsPath(true);
            renderer.setSeriesPaint(0, Color.RED);
            renderer.setSeriesPaint(1, Color.WHITE);
            renderer.setSeriesPaint(2, Color.GREEN);
            renderer.setSeriesPaint(3, Color.BLUE);
        }

        final XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
        renderer2.setBaseShapesVisible(false);
        renderer2.setBaseShapesFilled(false);
        renderer2.setDrawSeriesLineAsPath(true);
        renderer2.setSeriesPaint(0, Color.YELLOW);
        plot.setRenderer(1, renderer2);

        final DateAxis axis = (DateAxis) plot.getDomainAxis();
        
        if (isCustomRange) {
            Logs.myLogger.info("Creating custom range. Beginning date = {}. End date = {}",
                    begDate.toString(), endDate.toString());
            //Set the horizontal range
            axis.setRange(begDate, endDate);
            
            //Set the vertical range
            final List<Double> Values = new ArrayList<>();

            final TimeSeriesCollection collect = (TimeSeriesCollection) plot.getDataset(0);
            for (int i = 0; i < 4; i++) {
                final int[] closeBegValues = collect.getSurroundingItems(i, begDate.getTime());
                final int[] closeEndValues = collect.getSurroundingItems(i, endDate.getTime());
                Values.add(plot.getDataset(0).getYValue(i,closeBegValues[0]));
                Values.add(plot.getDataset(0).getYValue(i,closeEndValues[0]));
            }
            
            double highest = Values.get(0), lowest = Values.get(0);
            for (int i = 0; i < Values.size(); i++) {
                if (Values.get(i) < lowest) {
                    lowest = Values.get(i);
                }
                if (Values.get(i) > highest) {
                    highest = Values.get(i);
                }
            }

            plot.getRangeAxis(0).setRange(lowest, highest+.1*Math.abs(lowest-highest));

            //second do it for the time series
            final TimeSeriesCollection collect2 = (TimeSeriesCollection) plot.getDataset(1);
                       
            lowest = plot.getDataset(1).getYValue(0,collect2.getSurroundingItems(0, begDate.getTime())[0]); 
            highest = plot.getDataset(1).getYValue(0,collect2.getSurroundingItems(0, endDate.getTime())[0]);

            for (long i = begDate.getTime(); i < endDate.getTime(); i = i + 86400000) {
                final int[] closeValues = collect2.getSurroundingItems(0, i);
                
                if (plot.getDataset(1).getYValue(0,closeValues[0]) < lowest) {
                    lowest = plot.getDataset(1).getYValue(0,closeValues[0]);
                }
                if (plot.getDataset(1).getYValue(0,closeValues[0]) > highest) {
                    highest = plot.getDataset(1).getYValue(0,closeValues[0]);
                }                
            }
            
            plot.getRangeAxis(1).setRange(lowest-.1*Math.abs(lowest-highest), highest+.1*Math.abs(lowest-highest));
        }
        
        axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());

        return new ChartPanel(chart);
    }
   
    /**
     * 
     * @return 
     */
    private XYDataset createDataset2() {
        Logs.myLogger.info("Creating data set for the price data.");

        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        final TimeSeries s1 = new TimeSeries(selectionName + " Price Data");

        final List<Double> DoubleValues = new ArrayList<>();
        listToDouble(dailyPriceData, DoubleValues);

        for (int i = 0; i < dailyPriceDate.size(); i++) {
            DoubleValues.set(i, DoubleValues.get(i));
            final int[] DateValuesArray = new int[3];
            getDateValues(DateValuesArray, dailyPriceDate.get(i));

            s1.add( new Day(DateValuesArray[2], DateValuesArray[1], 
                    DateValuesArray[0]), 
                    DoubleValues.get(i));       
        }
        
        dataset.addSeries(s1);
        return dataset;
    }
    
    /**
     * 
     * @return 
     */
    private XYDataset createDataset() {
        Logs.myLogger.info("Creating data set for each window.");
        double highest = 0.0;
        final TimeSeriesCollection dataset = new TimeSeriesCollection();

        final TimeSeries[] TimeSeriesArray = new TimeSeries[4];
                    
        for (int i = 0; i < 4; i++) {

            TimeSeriesArray[i] = new TimeSeries(selectionName + 
                    Integer.toString(backtestDayLengths[i]) + 
                    " Bubble Index Data");

            final List<String> DateList = new ArrayList<>();
            final List<String> DataListString = new ArrayList<>();
            final List<Double> DataListDouble = new ArrayList<>();

            final String previousFilePath = Indices.userDir + "ProgramData" + 
                    Indices.filePathSymbol + categoryName + Indices.filePathSymbol + 
                    selectionName + Indices.filePathSymbol + selectionName +
                    Integer.toString(backtestDayLengths[i]) + "days.csv";

            if (Utilities.checkForFile(previousFilePath)) {

                Logs.myLogger.info("Found previous file = {}", previousFilePath);
                
                Utilities.ReadValues(previousFilePath, DataListString, 
                        DateList, true, true);             

                listToDouble(DataListString, DataListDouble);

                final double max = setMax(backtestDayLengths[i]);

                for (int j = 0; j < DataListDouble.size(); j++) {   
                    DataListDouble.set(j, DataListDouble.get(j) * 1.0 / max * 100);

                    final int[] DateValuesArray = new int[3];                       
                    getDateValues(DateValuesArray, DateList.get(j));                        

                    TimeSeriesArray[i].add( new Day(DateValuesArray[2], 
                        DateValuesArray[1], DateValuesArray[0]), 
                        DataListDouble.get(j));
                }

                if (Collections.max(DataListDouble) > highest) {
                    highest = Collections.max(DataListDouble);
                }
            }
            
            dataset.addSeries(TimeSeriesArray[i]);        
        }
        
        return dataset;
    }
    
    /**
     * 
     * @param DateValues
     * @param DateString 
     */
    public void getDateValues(final int[] DateValues, final String DateString) {
        //format of string is YYYY-MM-DD
        final String[] temp = DateString.split("-");
        if (temp.length == DateValues.length) {
            for (int i = 0; i < temp.length; i++) {
                DateValues[i] = Integer.parseInt(temp[i]);
            }        
        }       
        else {
            Logs.myLogger.error("Invalid input. temp.lenght = {}, DateValues.lenght = {}.",
                    temp.length, DateValues.length);
        }
    }
    
    /**
     * 
     * @param DataListString
     * @param DataListDouble 
     */
    private void listToDouble(final List<String> DataListString, final List<Double> DataListDouble) {
        for (final String DataListString1 : DataListString) {
            DataListDouble.add(Double.parseDouble(DataListString1));
        }
    }
        /*
    * WZW override
    * Draw a dynamic crosshair(trace line-both axis)
    */
    private void drawRTCrosshair(){
        final Rectangle2D screenDataArea = chartPanel.getScreenDataArea(m_iX, m_iY);
        if(screenDataArea==null)return;

        final Graphics2D g2 = (Graphics2D) chartPanel.getGraphics();
        final int iDAMaxX = (int)screenDataArea.getMaxX();
        final int iDAMinX = (int)screenDataArea.getMinX();   
        final int iDAMaxY = (int)screenDataArea.getMaxY();
        final int iDAMinY = (int)screenDataArea.getMinY();

        g2.setXORMode(new Color(0xFFFF00));//Color of crosshair
        if(m_l2dCrosshairX!=null && m_l2dCrosshairY!=null){
            g2.draw(m_l2dCrosshairX);
            g2.draw(m_l2dCrosshairY);
        }
        final Line2D l2dX = new Line2D.Double(m_dX, iDAMinY, m_dX, iDAMaxY);
        g2.draw(l2dX); m_l2dCrosshairX  =l2dX;       
        final Line2D l2dY = new Line2D.Double(iDAMinX, m_dY, iDAMaxX, m_dY);
        g2.draw(l2dY); m_l2dCrosshairY = l2dY;       
        g2.dispose();
    }
    
    /*
* WZW override
* Draw a realtime tooltip at top banner(store X,Y,... value)
*/
    private void drawRTInfo(){
        final Rectangle2D screenDataArea = chartPanel.getScreenDataArea(this.m_iX, this.m_iY);

        if(screenDataArea==null)return;

        final Graphics2D g2 = (Graphics2D) chartPanel.getGraphics();       
        final int iDAMinX = (int)screenDataArea.getMinX();   
        final int iDAMinY = (int)screenDataArea.getMinY();

        final XYPlot plot = (XYPlot) chartPanel.getChart().getPlot();
        final DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
        final ValueAxis rangeAxis  = plot.getRangeAxis();
        final RectangleEdge domainAxisEdge = plot.getDomainAxisEdge();
        final RectangleEdge rangeAxisEdge  = plot.getRangeAxisEdge();
    
        final double dXX = dateAxis.java2DToValue(this.m_dX, screenDataArea, domainAxisEdge);
        final double dYY = rangeAxis.java2DToValue(this.m_dY, screenDataArea, rangeAxisEdge);
        this.m_dXX = dXX;
        this.m_dYY = dYY;       
        //^Get title and data
        final ArrayList<String> alInfo = getInfo();
        final int iLenInfo = alInfo.size();       
        //^Customize dynamic tooltip display
        String[] asV;
        String sT, sV;
        final FontMetrics fontMetrics = g2.getFontMetrics();
        final int iFontHgt = fontMetrics.getHeight();     

        g2.setColor(Color.BLACK);
        g2.fillRect(iDAMinX+1, iDAMinY,100, 18*iFontHgt); //+1/-1 = offset 

        g2.setColor(Color.WHITE);

        final TimeSeriesCollection collect = (TimeSeriesCollection) plot.getDataset(0);

        for (int i = 0; i < collect.getSeriesCount(); i++) {
            if (this.m_dXX < collect.getXValue(i,0)) {
                this.indexValue[i] = 0.0;
            }

            else {
                final int[] closeValues = collect.getSurroundingItems(i, (long) this.m_dXX);
                this.indexValue[i] = plot.getDataset(0).getYValue(i,closeValues[0]);
            }    
        }
        
        final TimeSeriesCollection collect2 = (TimeSeriesCollection) plot.getDataset(1);

        if (this.m_dXX < collect2.getXValue(0,0)) {
            this.index4Value = 0.0;
        }
        
        else {
            int[] closeValues = collect2.getSurroundingItems(0, (long) this.m_dXX);
            this.index4Value = plot.getDataset(1).getYValue(0, closeValues[0]);
        }
    
        for(int i=iLenInfo-1; i>=0; i--){
            asV = alInfo.get(i).split("\\|");
            sT = asV[0];
            sV = asV[1];
            g2.drawString(sT, iDAMinX+20, iDAMinY+(i*3+1)*iFontHgt);
            g2.drawString(sV, iDAMinX+20,iDAMinY+(i*3+2)*iFontHgt);
        }
        
        g2.dispose();
    }

    /*
    * WZW override
    * get Info
    */
    private ArrayList<String> getInfo(){
        final DecimalFormat dfV = new DecimalFormat("#,###,###,##0.0000");
        final String[] asT = new String[]{selectionName + 
                Integer.toString(backtestDayLengths[3]) + ":", selectionName + 
                Integer.toString(backtestDayLengths[2])+ ":", selectionName + 
                Integer.toString(backtestDayLengths[1])+ ":", selectionName + 
                Integer.toString(backtestDayLengths[0])+ ":","Price:","Date:"};
        
        final int iLenT = asT.length;
        final ArrayList<String> alV = new ArrayList<>();
        String sV = "";
        //^Binding
        for(int i=iLenT-1; i>=0; i--){
            switch(i){
                case 0: sV = String.valueOf(dfV.format(this.indexValue[3])); break;
                case 1: sV = String.valueOf(dfV.format(this.indexValue[2])); break;
                case 2: sV = String.valueOf(dfV.format(this.indexValue[1])); break;
                case 3: sV = String.valueOf(dfV.format(this.indexValue[0])); break;
                case 4: sV = String.valueOf(dfV.format(this.index4Value)); break;               
                case 5: sV = getHMS(); break; //^Customize display for timeseries(intraday) only
            }
        
            alV.add(asT[i]+"|"+sV);        
        }   
        return alV;
    }
    
    /* WZW override
    * get Hour Minute Seconds
    */
    private String getHMS(){

        final long lDte = (long)this.m_dXX;
        final Date dtXX = new Date(lDte);
        final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        final String date = dateformat.format(dtXX);

        return date;
    }

    /**
     * 
     * @param dayLength
     * @return 
     */
    private double setMax(final int dayLength) {
        return pow((double)dayLength,3.353178) * exp(-8.949354) * 2.0 + 350;
    }
    
    /**
     * 
     * @throws IOException 
     */
    public void checkFiles() throws IOException {
        for (int i = 0; i < 4; i++) {
            final String filePath = Indices.userDir + "ProgramData" + Indices.filePathSymbol +
                    categoryName + Indices.filePathSymbol + selectionName + 
                    Indices.filePathSymbol + selectionName + 
                    Integer.toString(backtestDayLengths[i]) + "days.csv";
            
            if (!Utilities.checkForFile(filePath)) {
                Logs.myLogger.error("Could not find file. file path = {}", filePath);
                throw new IOException();
            }        
        }
    }
}