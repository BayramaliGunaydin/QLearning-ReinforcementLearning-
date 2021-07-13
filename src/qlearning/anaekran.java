/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlearning;

import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Color.blue;
import static java.awt.Color.green;
import static java.awt.Color.red;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import static javax.swing.JSplitPane.TOP;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYBarDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.SortOrder;

/**
 *
 * @author Bayram
 */
public class anaekran extends javax.swing.JFrame {
    
    String Baglanti="C:\\Users\\Bayram\\Desktop";
    double epsilon=0.2;
    int sayac=0;
    int reward=0;
    int adımsayisi=0;
    int delay;
    int Bitisx;
    int Bitisy;
    int Baslangicx;
    int Baslangicy;
    int secim=0;
    int hamle;
    
    Ajan Ajan=new Ajan();
    
    boolean durum=false;
    boolean durum2=false;
    
    XYSeries series = new XYSeries("XYGraph");
    XYSeriesCollection dataset = new XYSeriesCollection();   
    XYBarDataset barDataset = new XYBarDataset(dataset, 1);
    JFreeChart chart = ChartFactory.createXYBarChart("Episode Via Step", "Episode", false, "Step", barDataset, PlotOrientation.VERTICAL, false, true, false);
    XYPlot plot = (XYPlot) chart.getPlot();
    NumberAxis aa=(NumberAxis) plot.getDomainAxis();
    
    XYSeries series2 = new XYSeries("XYGraph");
    XYSeriesCollection dataset2 = new XYSeriesCollection();   
    XYBarDataset barDataset2 = new XYBarDataset(dataset2, 1);
    JFreeChart chart2 = ChartFactory.createXYBarChart("Episode Via Cost", "Episode", false, "Cost", barDataset2, PlotOrientation.VERTICAL, false, true, false);
    XYPlot plot2 = (XYPlot) chart2.getPlot();
    NumberAxis aa2=(NumberAxis) plot2.getDomainAxis();
    NumberAxis aa22=(NumberAxis) plot2.getRangeAxis();
    
    JButton bas=new JButton();
    JButton buton[][]=new JButton[50][50];
    
    final int[][] engelmatris=new int[50][50];
    int[][] RMatris=new int[2500][2500];
    double[][] QMatris=new double[2500][2500]; 
    
    Random r=new Random();
    
    Thread Final=new Thread(){
        synchronized public void run(){           
          while(!Final.isInterrupted()){
              int hamle=1;            
                if(durum2==true){                   
                Ajan.setKonumx(Baslangicx);
                Ajan.setKonumy(Baslangicy);              
                buton[Bitisx][Bitisy].setBackground(red);
        while(!(Ajan.getKonumx()==Bitisx && Ajan.getKonumy()==Bitisy)){
            
            try {               
                
                if(hamle==1){
                    Thread.sleep(1000);
                }
                else{
                    Thread.sleep(1);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(anaekran.class.getName()).log(Level.SEVERE, null, ex);
               
            }           
            int yön=ÖgrenilenAksiyon(Ajan.getKonumx(),Ajan.getKonumy());               
            Hareket(yön,Ajan.getKonumx(),Ajan.getKonumy());           
            buton[Ajan.getKonumx()][Ajan.getKonumy()].setBackground(Color.GREEN);
            if(Ajan.getKonumx()==Bitisx&&Ajan.getKonumy()==Bitisy){
                buton[Ajan.getKonumx()][Ajan.getKonumy()].setBackground(red);
                durum2=false;
                break;
            }
            if(durum2==false){
                buton[Ajan.getKonumx()][Ajan.getKonumy()].setBackground(Color.white);
                break;
            }           
            hamle++;
            jLabel6.setText("Adım Sayısı: "+hamle);
        }
        }         
          }
        }
        };
    Thread Hareket=new Thread(){
        synchronized public void run(){
            int bassari=0;
            int episode=0;
            int episode2=0;
            int cx=Bitisx,cy=Bitisy;         
            int i=0;
            
            while(!Hareket.isInterrupted()){
                if(durum==true){
               
                hamle=0;
                jLabel4.setText(sayac+".iterasyon");
                if(bassari>5000){
                    bassari=0;
                    reward=0;
                    plot.setRangeGridlinePaint(Color.black);
      ((XYBarRenderer)plot.getRenderer()).setBarPainter(new StandardXYBarPainter());
      XYBarRenderer barRenderer = (XYBarRenderer)plot.getRenderer();
      barRenderer.setSeriesPaint(0, Color.blue);
      int count=series.getItemCount();

      aa.setTickUnit(new NumberTickUnit(50000));
      dataset.addSeries(series);
      ChartPanel chartt=new ChartPanel(chart);
      jPanel2.add(chartt);
       
       
      plot2.setRangeGridlinePaint(Color.black);
      ((XYBarRenderer)plot2.getRenderer()).setBarPainter(new StandardXYBarPainter());
      XYBarRenderer barRenderer2 = (XYBarRenderer)plot2.getRenderer();
      barRenderer2.setSeriesPaint(0, Color.red);
      double low=series2.getMinY();
      barRenderer2.setBase(-10-0.2);

      aa2.setTickUnit(new NumberTickUnit(50000));
      aa22.setTickUnit(new NumberTickUnit(20));
      dataset2.addSeries(series2);
      ChartPanel chartt2=new ChartPanel(chart2);
      jPanel3.add(chartt2);
                    durum=false;
                    durum2=true; 
                    episode=0;           
                    continue;
                }
                jLabel5.setText("Başarılı İterasyon: "+bassari);
                int a=r.nextInt(50);
                int b=r.nextInt(50);
                while(engelmatris[a][b]==-1||(Bitisx==a&&Bitisy==b)){
                     a=r.nextInt(50);
                     b=r.nextInt(50);                 
                }
           //     Ajan.setKonumx(a);
            //   Ajan.setKonumy(b);
               Ajan.setKonumx(Baslangicx);
                Ajan.setKonumy(Baslangicy);
            
                buton[Bitisx][Bitisy].setBackground(red);
              reward=0;  
        while(true){
            hamle++;          
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(anaekran.class.getName()).log(Level.SEVERE, null, ex);
            }
        double greedy=r.nextDouble();        
            int yön;
    //  double epsilon=0.25;
           
              if(greedy<epsilon){
                yön=rastgele(Ajan.getKonumx(),Ajan.getKonumy());
           }else{
              
                 yön=ÖgrenilenAksiyon(Ajan.getKonumx(),Ajan.getKonumy());
            }               
            Hareket(yön,Ajan.getKonumx(),Ajan.getKonumy());
            buton[Ajan.getKonumx()][Ajan.getKonumy()].setBackground(Color.GREEN);
           if(engelmatris[Ajan.getKonumx()][Ajan.getKonumy()]==-1){
                buton[cx][cy].setBackground(Color.black);
                buton[Ajan.getKonumx()][Ajan.getKonumy()].setBackground(Color.magenta);
                cx=Ajan.getKonumx();
                cy=Ajan.getKonumy();            
                series.add(episode, hamle);
                series2.add(episode, reward);
               episode++;           
                break;
            }
            else if(hamle>10000){
                buton[Ajan.getKonumx()][Ajan.getKonumy()].setBackground(Color.white);
                series.add(episode, hamle);
                series2.add(episode, reward);                           
                 episode++;
                break;
            }
           else if(Ajan.getKonumx()==Bitisx && Ajan.getKonumy()==Bitisy){
                series.add(episode, hamle);
                series2.add(episode, reward);            
                bassari++;               
                 episode++;
                break;
            }
            if(durum==false){
                buton[Ajan.getKonumx()][Ajan.getKonumy()].setBackground(Color.white);                
                break;
            }
           
        }
        sayac++;
        reward=0;
        }               
            }
        }
        };
    
    private static void DosyaAc(String data) {
        File file = new File(data);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);           
        } catch (IOException e) {
            e.printStackTrace();
        }finally{          
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static void DosyaYaz(String data,String File,int satir) {       
        PrintWriter pw = null;
  try {
     File file = new File(File);
     FileWriter fw = new FileWriter(file, true);
     pw = new PrintWriter(fw);
     if(satir==1){
     pw.print(data);}
     else{
     pw.println(data);
     }
  } catch (IOException e) {
     e.printStackTrace();
  } finally {
     if (pw != null) {
        pw.close();
     }
  }
    }  
    public void reset(){
        for (int i = 0; i < buton.length; i++) {
            for (int j = 0; j < buton.length; j++) {
                if(engelmatris[j][i]==-1){
                    buton[j][i].setBackground(Color.black);
                }
                else if(Bitisy==i&&Bitisx==j){
                    buton[j][i].setBackground(red);
                }
                else if(Baslangicy==i&&Baslangicx==j){
                    buton[j][i].setBackground(blue);
                }
                else{
                    buton[j][i].setBackground(Color.white);
                }
                
            }
            
        }
        
    }
    public double QMaxx(int x,int y){
    ArrayList<Double> sonraki=new ArrayList<Double>();
    double enb;
    if(x-1>-1){
        sonraki.add(QMatris[x+y*50][(x+y*50)-1]);
    }
    if(x+1<50){
        sonraki.add(QMatris[x+y*50][(x+y*50)+1]);
    }
    if(y-1>-1){
        sonraki.add(QMatris[x+y*50][(x+y*50)-50]);
    }
    if(y+1<50){
        sonraki.add(QMatris[x+y*50][(x+y*50)+50]);
    }
    if(x-1>-1&&y-1>-1){
        sonraki.add(QMatris[x+y*50][(x+y*50)-51]);
    }
    if(x+1<50&&y-1>-1){
        sonraki.add(QMatris[x+y*50][(x+y*50)-49]);
    }
    if(x-1>-1&&y+1<50){
        sonraki.add(QMatris[x+y*50][(x+y*50)+49]);
    }
    if(x+1<50&&y+1<50){
        sonraki.add(QMatris[x+y*50][(x+y*50)+51]);
    }
    
    
    
        enb=sonraki.get(0);
        for (int i = 0; i < sonraki.size(); i++) {
            if(sonraki.get(i)>enb){
                enb=sonraki.get(i);
            }           
        }
     return enb;
    }
    public int ÖgrenilenAksiyon(int x,int y){          
            int yön=-1;
            double yukari=-1,asagi=-1,sag=-1,sol=-1,yukarisol=-1,yukarisag=-1,asagisag=-1,asagisol=-1;
            double enb=-999;            
        ArrayList<Integer> yönarray=new ArrayList<Integer>();
        if(y!=0){
            yönarray.add(0);
            yukari=QMatris[x+y*50][(x+y*50)-50];         
         }
        if(x!=0){
            yönarray.add(3);
        sol=QMatris[x+y*50][(x+y*50)-1];
        }
        if(x!=49){
            yönarray.add(1);
        sag=QMatris[x+y*50][(x+y*50)+1];
        }
        if(y!=49){
            yönarray.add(2);
        asagi=QMatris[x+y*50][(x+y*50)+50];
        }
        if(x-1>-1&&y-1>-1){
            yönarray.add(4);
        yukarisol=QMatris[x+y*50][(x+y*50)-51];
    }
        if(x+1<50&&y-1>-1){
            yönarray.add(5);
        yukarisag=QMatris[x+y*50][(x+y*50)-49];
    }
        if(x-1>-1&&y+1<50){
            yönarray.add(7);
        asagisol=QMatris[x+y*50][(x+y*50)+49];
    }
        if(x+1<50&&y+1<50){
            yönarray.add(6);
        asagisag=QMatris[x+y*50][(x+y*50)+51];
    }

        Collections.shuffle(yönarray);
        for (int i = 0; i < yönarray.size(); i++) {
          //  System.out.print("--"+yönarray.get(i));
            if(yönarray.get(i)==0&&yukari>enb){
                enb=yukari;
                yön=0;
            }
            if(yönarray.get(i)==1&&sag>enb){
                enb=sag;
                yön=1;
            }
            if(yönarray.get(i)==2&&asagi>enb){
                enb=asagi;
                yön=2;
            }
            if(yönarray.get(i)==3&&sol>enb){
                enb=sol;
                yön=3;
            }
            if(yönarray.get(i)==4&&yukarisol>enb){
                enb=yukarisol;
                yön=4;
            }
            if(yönarray.get(i)==5&&yukarisag>enb){
                enb=yukarisag;
                yön=5;
            }
            if(yönarray.get(i)==6&&asagisag>enb){
                enb=asagisag;
                yön=6;
            }
            if(yönarray.get(i)==7&&asagisol>enb){
                enb=asagisol;
                yön=7;
            }
            
        }
     //   System.out.println("");
       /* if(yön==0){
                QMatris[x+y*50][(x+y*50)-50]=RMatris[x+y*50][(x+y*50)-50]+0.9*QMaxx(x,y-1);
            }
        else if(yön==1){                 
                QMatris[x+y*50][(x+y*50)+1]=RMatris[x+y*50][(x+y*50)+1]+0.9*QMaxx(x+1,y);
            }
        else if(yön==2){               
                QMatris[x+y*50][(x+y*50)+50]=RMatris[x+y*50][(x+y*50)+50]+0.9*QMaxx(x,y+1);
            }
        else if(yön==3){                  
                QMatris[x+y*50][(x+y*50)-1]=RMatris[x+y*50][(x+y*50)-1]+0.9*QMaxx(x-1,y);
            } 
        else if(yön==4){
                QMatris[x+y*50][(x+y*50)-51]=RMatris[x+y*50][(x+y*50)-51]+0.9*QMaxx(x-1,y-1);
            } 
        else if(yön==5){
                QMatris[x+y*50][(x+y*50)-49]=RMatris[x+y*50][(x+y*50)-49]+0.9*QMaxx(x+1,y-1);
            } 
        else if(yön==6){
                QMatris[x+y*50][(x+y*50)+51]=RMatris[x+y*50][(x+y*50)+51]+0.9*QMaxx(x+1,y+1);
            }
        else if(yön==7){
                QMatris[x+y*50][(x+y*50)+49]=RMatris[x+y*50][(x+y*50)+49]+0.9*QMaxx(x-1,y+1);
            } */
        
        
        if(yön==0){
                QMatris[x+y*50][(x+y*50)-50]=QMatris[x+y*50][(x+y*50)-50]+0.9*(RMatris[x+y*50][(x+y*50)-50]+0.9*QMaxx(x,y-1)-QMatris[x+y*50][(x+y*50)-50]);
          //  if(RMatris[x+y*50][(x+y*50)-50]!=-1){                
                reward=reward-RMatris[x+y*50][(x+y*50)-50];
          //  }
                
        }
        else if(yön==1){                 
                QMatris[x+y*50][(x+y*50)+1]=QMatris[x+y*50][(x+y*50)+1]+0.9*(RMatris[x+y*50][(x+y*50)+1]+0.9*QMaxx(x+1,y)-QMatris[x+y*50][(x+y*50)+1]);
        //   if(RMatris[x+y*50][(x+y*50)+1]!=-1){                
                reward=reward-RMatris[x+y*50][(x+y*50)+1];
        //    }
                
        }
        else if(yön==2){               
                QMatris[x+y*50][(x+y*50)+50]=QMatris[x+y*50][(x+y*50)+50]+0.9*(RMatris[x+y*50][(x+y*50)+50]+0.9*QMaxx(x,y+1)-QMatris[x+y*50][(x+y*50)+50]);
       //     if(RMatris[x+y*50][(x+y*50)+50]!=-1){
                
                reward=reward-RMatris[x+y*50][(x+y*50)+50];
       //     }
                
        }
        else if(yön==3){                  
                QMatris[x+y*50][(x+y*50)-1]=QMatris[x+y*50][(x+y*50)-1]+0.9*(RMatris[x+y*50][(x+y*50)-1]+0.9*QMaxx(x-1,y)-QMatris[x+y*50][(x+y*50)-1]);
       //     if(RMatris[x+y*50][(x+y*50)-1]!=-1){                
                reward=reward-RMatris[x+y*50][(x+y*50)-1];
       //     }
                
        } 
        else if(yön==4){
                QMatris[x+y*50][(x+y*50)-51]=QMatris[x+y*50][(x+y*50)-51]+0.9*(RMatris[x+y*50][(x+y*50)-51]+0.9*QMaxx(x-1,y-1)-QMatris[x+y*50][(x+y*50)-51]);
            
        //        if(RMatris[x+y*50][(x+y*50)-51]!=-1){
                reward=reward-RMatris[x+y*50][(x+y*50)-51];
        //    }
                
        } 
        else if(yön==5){
                QMatris[x+y*50][(x+y*50)-49]=QMatris[x+y*50][(x+y*50)-49]+0.9*(RMatris[x+y*50][(x+y*50)-49]+0.9*QMaxx(x+1,y-1)-QMatris[x+y*50][(x+y*50)-49]);
       //     if(RMatris[x+y*50][(x+y*50)-49]!=-1){
                reward=reward-RMatris[x+y*50][(x+y*50)-49];
       //     }
                
        } 
        else if(yön==6){
                QMatris[x+y*50][(x+y*50)+51]=QMatris[x+y*50][(x+y*50)+51]+0.9*(RMatris[x+y*50][(x+y*50)+51]+0.9*QMaxx(x+1,y+1)-QMatris[x+y*50][(x+y*50)+51]);
      //     if(RMatris[x+y*50][(x+y*50)+51]!=-1){
                reward=reward-RMatris[x+y*50][(x+y*50)+51];
       //     }
                
        }
        else if(yön==7){
                QMatris[x+y*50][(x+y*50)+49]=QMatris[x+y*50][(x+y*50)+49]+0.9*(RMatris[x+y*50][(x+y*50)+49]+0.9*QMaxx(x-1,y+1)-QMatris[x+y*50][(x+y*50)+49]);
            
       //        if(RMatris[x+y*50][(x+y*50)+49]!=-1){
                reward=reward-RMatris[x+y*50][(x+y*50)+49];
        //    }
                    
        } 
        
      //  System.out.println("yön:"+yön);
        
    
    return yön;
    }
    public void Hareket(int yön,int x,int y){
        int konumx;
        int konumy;
        if(yön==0){
            
                Ajan.setKonumx(x);
                Ajan.setKonumy(y-1); 
            //    buton[x][y].setBackground(Color.white);
            
        }
        if(yön==1){
                         
                Ajan.setKonumx(x+1);
                Ajan.setKonumy(y);
            //    buton[x][y].setBackground(Color.white);
            
        }
        if(yön==2){
            
                Ajan.setKonumx(x);
                Ajan.setKonumy(y+1);
            //    buton[x][y].setBackground(Color.white);
            
        }
        if(yön==3){
            
                Ajan.setKonumx(x-1);
                Ajan.setKonumy(y);
             //   buton[x][y].setBackground(Color.white);
            
        }
        if(yön==4){
            
                Ajan.setKonumx(x-1);
                Ajan.setKonumy(y-1);
             //   buton[x][y].setBackground(Color.white);
            
        }
        if(yön==5){
            
                Ajan.setKonumx(x+1);
                Ajan.setKonumy(y-1);
             //   buton[x][y].setBackground(Color.white);
            
        }
        if(yön==6){
            
                Ajan.setKonumx(x+1);
                Ajan.setKonumy(y+1);
             //   buton[x][y].setBackground(Color.white);
            
        }
        if(yön==7){
            
                Ajan.setKonumx(x-1);
                Ajan.setKonumy(y+1);
             //   buton[x][y].setBackground(Color.white);
            
        }
        if(durum==true){
        buton[x][y].setBackground(Color.white);}
        if(x==Baslangicx&&y==Baslangicy){
            buton[x][y].setBackground(blue);
        }
        if(x==Bitisx&&y==Bitisy){
            buton[x][y].setBackground(red);
        }
        if(engelmatris[x][y]==-1){
            buton[x][y].setBackground(Color.black);
        }
        
    }
    public int rastgele(int x,int y){
        ArrayList<Integer> yönarray=new ArrayList<Integer>();
        if(y!=0){
            yönarray.add(0);
                    
         }
        if(x!=0){
            yönarray.add(3);
        
        }
        if(x!=49){
            yönarray.add(1);
        
        }
        if(y!=49){
            yönarray.add(2);
       
        }
        if(x-1>-1&&y-1>-1){
            yönarray.add(4);
        
    }
        if(x+1<50&&y-1>-1){
            yönarray.add(5);
        
    }
        if(x-1>-1&&y+1<50){
            yönarray.add(7);
        
    }
        if(x+1<50&&y+1<50){
            yönarray.add(6);

    }
        int sec=r.nextInt(yönarray.size());
        
        if(yönarray.get(sec)==0){
                QMatris[x+y*50][(x+y*50)-50]=QMatris[x+y*50][(x+y*50)-50]+0.9*(RMatris[x+y*50][(x+y*50)-50]+0.9*QMaxx(x,y-1)-QMatris[x+y*50][(x+y*50)-50]);
             //  if(RMatris[x+y*50][(x+y*50)-50]!=-1){
                  reward=reward-RMatris[x+y*50][(x+y*50)-50];  
            //    }
               
                
                
            }
        else if(yönarray.get(sec)==1){                 
                QMatris[x+y*50][(x+y*50)+1]=QMatris[x+y*50][(x+y*50)+1]+0.9*(RMatris[x+y*50][(x+y*50)+1]+0.9*QMaxx(x+1,y)-QMatris[x+y*50][(x+y*50)+1]);
            //    if(RMatris[x+y*50][(x+y*50)+1]!=-1){
                 reward=reward-RMatris[x+y*50][(x+y*50)+1];   
             //   }
                
                
        
            }
        else if(yönarray.get(sec)==2){               
                QMatris[x+y*50][(x+y*50)+50]=QMatris[x+y*50][(x+y*50)+50]+0.9*(RMatris[x+y*50][(x+y*50)+50]+0.9*QMaxx(x,y+1)-QMatris[x+y*50][(x+y*50)+50]);
            //  if(RMatris[x+y*50][(x+y*50)+50]!=-1){
                  reward=reward-RMatris[x+y*50][(x+y*50)+50]; 
             //   }
                
                
        
            }
        else if(yönarray.get(sec)==3){                  
                QMatris[x+y*50][(x+y*50)-1]=QMatris[x+y*50][(x+y*50)-1]+0.9*(RMatris[x+y*50][(x+y*50)-1]+0.9*QMaxx(x-1,y)-QMatris[x+y*50][(x+y*50)-1]);
             //   if(RMatris[x+y*50][(x+y*50)-1]!=-1){
                  reward=reward-RMatris[x+y*50][(x+y*50)-1];  
             //   }
               
                
        
            } 
        else if(yönarray.get(sec)==4){
                QMatris[x+y*50][(x+y*50)-51]=QMatris[x+y*50][(x+y*50)-51]+0.9*(RMatris[x+y*50][(x+y*50)-51]+0.9*QMaxx(x-1,y-1)-QMatris[x+y*50][(x+y*50)-51]);
             //   if(RMatris[x+y*50][(x+y*50)-51]!=-1){
                  reward=reward-RMatris[x+y*50][(x+y*50)-51];  
             //   }
                
                
        
            } 
        else if(yönarray.get(sec)==5){
                QMatris[x+y*50][(x+y*50)-49]=QMatris[x+y*50][(x+y*50)-49]+0.9*(RMatris[x+y*50][(x+y*50)-49]+0.9*QMaxx(x+1,y-1)-QMatris[x+y*50][(x+y*50)-49]);
             //    if(RMatris[x+y*50][(x+y*50)-49]!=-1){
                   reward=reward-RMatris[x+y*50][(x+y*50)-49]; 
             //   }
                
                
        
            } 
        else if(yönarray.get(sec)==6){
                QMatris[x+y*50][(x+y*50)+51]=QMatris[x+y*50][(x+y*50)+51]+0.9*(RMatris[x+y*50][(x+y*50)+51]+0.9*QMaxx(x+1,y+1)-QMatris[x+y*50][(x+y*50)+51]);
              //  if(RMatris[x+y*50][(x+y*50)+51]!=-1){
                   reward=reward-RMatris[x+y*50][(x+y*50)+51]; 
              //  }
                
                
        
            }
        else if(yönarray.get(sec)==7){
                QMatris[x+y*50][(x+y*50)+49]=QMatris[x+y*50][(x+y*50)+49]+0.9*(RMatris[x+y*50][(x+y*50)+49]+0.9*QMaxx(x-1,y+1)-QMatris[x+y*50][(x+y*50)+49]);
            //   if(RMatris[x+y*50][(x+y*50)+49]!=-1){
                  reward=reward-RMatris[x+y*50][(x+y*50)+49];  
             //   }
                
                
        
            } 
        
        
        
        
        
        return yönarray.get(sec);
        
    }  
    public int rastgele2(int x,int y){
        ArrayList<Integer> yönarray=new ArrayList<Integer>();
        if(y!=0&&QMatris[x+y*50][(x+y*50)-50]>=0){
            yönarray.add(0);                   
         }
        if(x!=0&&QMatris[x+y*50][(x+y*50)-1]>=0){
            yönarray.add(3);        
        }
        if(x!=49&&QMatris[x+y*50][(x+y*50)+1]>=0){
            yönarray.add(1);       
        }
        if(y!=49&&QMatris[x+y*50][(x+y*50)+50]>=0){
            yönarray.add(2);       
        }
        if(x-1>-1&&y-1>-1&&QMatris[x+y*50][(x+y*50)-51]>=0){
            yönarray.add(4);        
    }
        if(x+1<50&&y-1>-1&&QMatris[x+y*50][(x+y*50)-49]>=0){
            yönarray.add(5);        
    }
        if(x-1>-1&&y+1<50&&QMatris[x+y*50][(x+y*50)+49]>=0){
            yönarray.add(7);       
    }
        if(x+1<50&&y+1<50&&QMatris[x+y*50][(x+y*50)+51]>=0){
            yönarray.add(6);
    }
        int sec=r.nextInt(yönarray.size());
        
        if(yönarray.get(sec)==0){
                QMatris[x+y*50][(x+y*50)-50]=QMatris[x+y*50][(x+y*50)-50]+0.9*(RMatris[x+y*50][(x+y*50)-50]+0.9*QMaxx(x,y-1)-QMatris[x+y*50][(x+y*50)-50]);
          /*      if(RMatris[x+y*50][(x+y*50)-50]==0){
                    reward=reward+3;
                }
                else*/
                reward=reward+RMatris[x+y*50][(x+y*50)-50];
                
            }
        else if(yönarray.get(sec)==1){                 
                QMatris[x+y*50][(x+y*50)+1]=QMatris[x+y*50][(x+y*50)+1]+0.9*(RMatris[x+y*50][(x+y*50)+1]+0.9*QMaxx(x+1,y)-QMatris[x+y*50][(x+y*50)+1]);
             /*   if(RMatris[x+y*50][(x+y*50)+1]==0){
                    reward=reward+3;
                }
                else*/
                reward=reward+RMatris[x+y*50][(x+y*50)+1];
        
            }
        else if(yönarray.get(sec)==2){               
                QMatris[x+y*50][(x+y*50)+50]=QMatris[x+y*50][(x+y*50)+50]+0.9*(RMatris[x+y*50][(x+y*50)+50]+0.9*QMaxx(x,y+1)-QMatris[x+y*50][(x+y*50)+50]);
             /*  if(RMatris[x+y*50][(x+y*50)+50]==0){
                    reward=reward+3;
                }
                else*/
                reward=reward+RMatris[x+y*50][(x+y*50)+50];
        
            }
        else if(yönarray.get(sec)==3){                  
                QMatris[x+y*50][(x+y*50)-1]=QMatris[x+y*50][(x+y*50)-1]+0.9*(RMatris[x+y*50][(x+y*50)-1]+0.9*QMaxx(x-1,y)-QMatris[x+y*50][(x+y*50)-1]);
             /*   if(RMatris[x+y*50][(x+y*50)-1]==0){
                    reward=reward+3;
                }
                else*/
                reward=reward+RMatris[x+y*50][(x+y*50)-1];
        
            } 
        else if(yönarray.get(sec)==4){
                QMatris[x+y*50][(x+y*50)-51]=QMatris[x+y*50][(x+y*50)-51]+0.9*(RMatris[x+y*50][(x+y*50)-51]+0.9*QMaxx(x-1,y-1)-QMatris[x+y*50][(x+y*50)-51]);
          /*      if(RMatris[x+y*50][(x+y*50)-51]==0){
                    reward=reward+3;
                }
                else*/
                reward=reward+RMatris[x+y*50][(x+y*50)-51];
        
            } 
        else if(yönarray.get(sec)==5){
                QMatris[x+y*50][(x+y*50)-49]=QMatris[x+y*50][(x+y*50)-49]+0.9*(RMatris[x+y*50][(x+y*50)-49]+0.9*QMaxx(x+1,y-1)-QMatris[x+y*50][(x+y*50)-49]);
           /*      if(RMatris[x+y*50][(x+y*50)-49]==0){
                    reward=reward+3;
                }
                else*/
                reward=reward+RMatris[x+y*50][(x+y*50)-49];
        
            } 
        else if(yönarray.get(sec)==6){
                QMatris[x+y*50][(x+y*50)+51]=QMatris[x+y*50][(x+y*50)+51]+0.9*(RMatris[x+y*50][(x+y*50)+51]+0.9*QMaxx(x+1,y+1)-QMatris[x+y*50][(x+y*50)+51]);
          /*      if(RMatris[x+y*50][(x+y*50)+51]==0){
                    reward=reward+3;
                }
                else*/
                reward=reward+RMatris[x+y*50][(x+y*50)+51];
        
            }
        else if(yönarray.get(sec)==7){
                QMatris[x+y*50][(x+y*50)+49]=QMatris[x+y*50][(x+y*50)+49]+0.9*(RMatris[x+y*50][(x+y*50)+49]+0.9*QMaxx(x-1,y+1)-QMatris[x+y*50][(x+y*50)+49]);
         /*       if(RMatris[x+y*50][(x+y*50)+49]==0){
                    reward=reward+3;
                }
                else*/
                reward=reward+RMatris[x+y*50][(x+y*50)+49];
        
            } 
        
        
        
        
        
        return yönarray.get(sec);
    }
    public void RMatris(int[][] engelmatris){
        for (int i = 0; i <50; i++) {
            for (int j = 0; j <50; j++) {
                if(engelmatris[i][j]==-1){
                    if(i!=0){                   
                   RMatris[(i+j*50)-1][i+j*50]=-10;
                    }
                    if(i!=49){                    
                   RMatris[(i+j*50)+1][i+j*50]=-10;
                    }
                    if(j!=0){
                   RMatris[(i+j*50)-50][i+j*50]=-10; 
                    }
                    if(j!=49){
                   RMatris[(i+j*50)+50][i+j*50]=-10;
                    }
                    if(j<49&&i>0){
                   RMatris[(i+j*50)+49][i+j*50]=-10; 
                    }
                    if(j<49&&i<49){
                   RMatris[(i+j*50)+51][i+j*50]=-10;    
                    }
                    if(j>0&&i>0){
                   RMatris[(i+j*50)-51][i+j*50]=-10;
                    }
                    if(i<49&&j>0){
                   RMatris[(i+j*50)-49][i+j*50]=-10;
                    }
                }
                if(i==Bitisx&&j==Bitisy){
                     if(i!=0){                   
                   RMatris[(i+j*50)-1][i+j*50]=10;
                    }
                    if(i!=49){                    
                   RMatris[(i+j*50)+1][i+j*50]=10;
                    }
                    if(j!=0){
                   RMatris[(i+j*50)-50][i+j*50]=10; 
                    }
                    if(j!=49){
                   RMatris[(i+j*50)+50][i+j*50]=10;
                    }
                    if(j<49&&i>0){
                   RMatris[(i+j*50)+49][i+j*50]=10; 
                    }
                    if(j<49&&i<49){
                   RMatris[(i+j*50)+51][i+j*50]=10;    
                    }
                    if(j>0&&i>0){
                   RMatris[(i+j*50)-51][i+j*50]=10;
                    }
                    if(i<49&&j>0){
                   RMatris[(i+j*50)-49][i+j*50]=10;
                    }
                   
                
                }
                
            }
            
        }
        
        
        
    }
    
    
    public anaekran() {
        for(int i=0;i<2500;i++){
    Arrays.fill(RMatris[i], -1);           
}
       initComponents();
        myinitComponents();
        Hareket.start();
        Final.start();
        DosyaAc(Baglanti+"\\engel.txt");
         for (int i = 0; i < buton.length; i++) {
            for (int j = 0; j < buton.length; j++) {
                if(engelmatris[j][i]==-1){
                   DosyaYaz("("+j+","+i+",Siyah)",Baglanti+"\\engel.txt",1);
                }
                else if(engelmatris[j][i]==0){
                   DosyaYaz("("+j+","+i+",Beyaz)",Baglanti+"\\engel.txt",1); 
                }
              /*  else if(engelmatris[j][i]==1){
                   DosyaYaz("("+j+","+i+",Baslangic)",Baglanti+"\\A.txt",1);
                }
                else if(engelmatris[j][i]==2){
                   DosyaYaz("("+j+","+i+",Bitis)",Baglanti+"\\A.txt",1);
                }*/
            }
            DosyaYaz("",Baglanti+"\\engel.txt",0);
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

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setEnabled(false);
        jPanel1.setMinimumSize(new java.awt.Dimension(1000, 1000));
        jPanel1.setPreferredSize(new java.awt.Dimension(1920, 1080));
        jPanel1.setLayout(null);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("Reset");
        jButton1.setRolloverEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(1440, 190, 80, 40);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Başlangıç Konumu Seç");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(1080, 70, 178, 24);

        jButton2.setBackground(new java.awt.Color(0, 51, 255));
        jButton2.setEnabled(false);
        jButton2.setPreferredSize(new java.awt.Dimension(40, 40));
        jPanel1.add(jButton2);
        jButton2.setBounds(1080, 140, 40, 40);

        jButton3.setBackground(new java.awt.Color(255, 0, 0));
        jButton3.setForeground(new java.awt.Color(153, 0, 0));
        jButton3.setEnabled(false);
        jButton3.setPreferredSize(new java.awt.Dimension(40, 40));
        jPanel1.add(jButton3);
        jButton3.setBounds(1080, 210, 40, 40);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText(":    Başlangıç");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(1160, 150, 103, 22);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText(":    Bitiş");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(1160, 220, 63, 22);

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setText("Başla");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(1500, 120, 80, 40);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("0.iterasyon");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(1210, 20, 150, 17);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Başarılı İterasyon: 0");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(1360, 20, 200, 17);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(400, 400));
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 400));
        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPanel2);
        jPanel2.setBounds(1020, 310, 860, 340);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMinimumSize(new java.awt.Dimension(400, 400));
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 400));
        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPanel3);
        jPanel3.setBounds(1020, 690, 860, 340);

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton6.setText("Noktaları Sıfırla");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6);
        jButton6.setBounds(1550, 190, 130, 40);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Adım Sayısı: 0");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(1060, 20, 120, 17);

        jTextField1.setText("0");
        jPanel1.add(jTextField1);
        jTextField1.setBounds(1500, 70, 80, 20);

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton7.setText("Uygula");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7);
        jButton7.setBounds(1600, 60, 100, 40);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Delay(ms):");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(1400, 70, 80, 20);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1900, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public void myinitComponents(){ 
         int ay=20;
         for (int i = 0; i < 50; i++) {
             for (int j = 0; j < 50; j++) {
                 buton[j][i]=new javax.swing.JButton();
                 buton[j][i].setBounds(10+j*20, ay, 20,20);
                 buton[j][i].setVisible(true);
                 buton[j][i].setBackground(Color.white);
                 buton[j][i].setRolloverEnabled(false);
                 jPanel1.add(buton[j][i]);
                 final int ii=i;
                 final int jj=j;
                 
                 buton[j][i].addActionListener(new ActionListener(){  
public void actionPerformed(ActionEvent e){ 
           JButton btn = (JButton) (e.getSource());         
             //  btn.setBackground(Color.black);            
               btn.setEnabled(false);
                if(secim==1){
                   btn.setBackground(Color.red);
                   btn.setEnabled(false);                 
                   secim++;
                   Bitisx=(btn.getX()-10)/20;
                   Bitisy=(btn.getY()-20)/20;
                   jLabel1.setVisible(false);
                           RMatris(engelmatris);
                    for (int k = 0; k < 50; k++) {
                        for (int l = 0; l < 50; l++) {
                            buton[k][l].setEnabled(false);                          
                        }                        
                    }
               }
                else if(secim==0){
                   btn.setBackground(Color.blue);
                   btn.setEnabled(false);
                   
                   
                   jLabel1.setText("Bitiş Konumu Seç");
                   Baslangicx=(btn.getX()-10)/20;
                   Baslangicy=(btn.getY()-20)/20;                  
                  
                   secim++;
               }             
}  
}); 
             }
             ay+=20;
         }
       /*  engelmatris[Baslangicx][Baslangicy]=1;
         engelmatris[Bitisx][Bitisy]=2;*/
         Random r=new Random(); 
                
         int a=r.nextInt(2);
                 
                 for (int k = 0; k < 750; k++) {
                     int x=r.nextInt(50);
                     int y=r.nextInt(50);                    
                     if(engelmatris[y][x]==-1){
                         k--;
                         continue;
                     }
                     buton[y][x].setEnabled(false);
                     buton[y][x].setBackground(Color.black);
                     engelmatris[y][x]=-1;                     
                 }       
     }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
          try {
            Thread.sleep(700);
        } catch (InterruptedException ex) {
            Logger.getLogger(anaekran.class.getName()).log(Level.SEVERE, null, ex);
        }
            jButton4.setVisible(true);
            sayac=0;
            reset();
            durum=false;
            durum2=false;
    
        
            jPanel2.removeAll();
            jPanel3.removeAll();
            jPanel2.setVisible(false);
            jPanel2.setVisible(true);
            jPanel3.setVisible(false);
            jPanel3.setVisible(true);
            
            dataset.removeAllSeries();
            dataset2.removeAllSeries();
            series.clear();
            series2.clear();          
            double[][] reset=new double[2500][2500];  
            QMatris=reset;    
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jButton4.setVisible(false);
        durum=true;                
        sayac=0;
            
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        sayac=0;
        secim=0; 
        jButton4.setVisible(true);
        Bitisx=0;
        Bitisy=0;
        try {
            Thread.sleep(700);
        } catch (InterruptedException ex) {
            Logger.getLogger(anaekran.class.getName()).log(Level.SEVERE, null, ex);
        }
         reset();
        durum=false;
        durum2=false;
    
        jPanel2.removeAll();
        jPanel3.removeAll();
        jPanel2.setVisible(false);
        jPanel2.setVisible(true);
        jPanel3.setVisible(false);
        jPanel3.setVisible(true);
        XYSeries resetseries = new XYSeries("XYGraph");
        XYSeriesCollection resetdataset = new XYSeriesCollection(); 
        dataset.removeAllSeries();
        dataset2.removeAllSeries();
        series.clear();
        series2.clear();
        double[][] reset=new double[2500][2500];  
        
        QMatris=reset;
        for(int i=0;i<2500;i++){
          Arrays.fill(RMatris[i], -1);           
}
        buton[Baslangicx][Baslangicy].setBackground(Color.white);
        buton[Bitisx][Bitisy].setBackground(Color.white);
       
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if(engelmatris[i][j]!=-1){
                    buton[i][j].setEnabled(true);
                }
                
            }
            
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        delay=Integer.parseInt(jTextField1.getText());
    }//GEN-LAST:event_jButton7ActionPerformed
     
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(anaekran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(anaekran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(anaekran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(anaekran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new anaekran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
