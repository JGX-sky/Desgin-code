import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Memory extends JFrame {
    protected JTextField blank0,blank1,blank2,blank3,
                         blank4,blank5,blank6,blank7,
                         blank8,blank9;//定义10个进程块
    protected JTextField applyMemTF,releaseMemTF;
    protected JTextArea showMemStatusTF;
    protected JButton applyMemButton,releaseMemButton,emptyMemButton;

    int[] processBlock = new int[10];//表示进程块
    int[] processBlockStartAdd = new int[10];//表示存储起始地址
    int[] processBlockLength = new int[10];//表示存储进程长度

    public Memory() {
        JPanel p1 = new JPanel(new GridLayout(3,2,5,2));
        p1.add(applyMemButton = new JButton("申请（大小）"));
        p1.add(applyMemTF = new JTextField(3));
        p1.add(releaseMemButton = new JButton("释放（块号）"));
        p1.add(releaseMemTF = new JTextField(3));
        p1.add(new Label("\t内存分配情况"));  

        JPanel p2 = new JPanel(new GridLayout(2,1,2,2));
        p2.add(p1);
        p2.add(showMemStatusTF = new JTextArea());

        JPanel p3 = new JPanel(new GridLayout(11,1,20,0));
        p3.add(new JLabel("内存容量为10"));
        p3.add(blank0 = new JTextField(3));
        p3.add(blank1 = new JTextField(3));
        p3.add(blank2 = new JTextField(3));
        p3.add(blank3 = new JTextField(3));
        p3.add(blank4 = new JTextField(3));
        p3.add(blank5 = new JTextField(3));
        p3.add(blank6 = new JTextField(3));
        p3.add(blank7 = new JTextField(3));
        p3.add(blank8 = new JTextField(3));
        p3.add(blank9 = new JTextField(3));

        JPanel p4 = new JPanel(new BorderLayout(3,3));
        p4.add(p2,BorderLayout.WEST);
        p4.add(p3,BorderLayout.CENTER);

        JPanel p5 = new JPanel(new FlowLayout());
        p5.add(p4);
        p5.add(emptyMemButton = new JButton("清空内存"),BorderLayout.EAST);

        setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
        this.getContentPane().add(p5);

        Font font1 = new Font("SansSerif",Font.BOLD,16);
        applyMemTF.setFont(font1);
        releaseMemTF.setFont(font1);
        showMemStatusTF.setFont(font1); 

        applyMemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int n = Integer.parseInt(applyMemTF.getText());//进程块的大小
                if(n > 10 || n <0){
                    JOptionPane.showMessageDialog(null,"进程大小违规，请重新输入！");
                }

                outer://向内存中添加进程    
                for(int i = 0;i < 10; i++ ){//向内存中添加进程
                    if(processBlock[i] == 0 && Sum(processBlock,i,n) == 0){
                        processBlockStartAdd[i] = i;//存储起始地址
                        processBlockLength[i] = n;//存储进程长度

                        for(int ss = i;ss < (i + n);ss++)
                            processBlock[ss] = 1;//找到合适的位置，置1
                        colorr();
                        JOptionPane.showMessageDialog(null,"成功分配到内存！");
                        showMemStatusTF.append("块号：" + processBlockStartAdd[i] + " 起始位置：" +
                                processBlockStartAdd[i] + "大小： " + processBlockLength[i] +"\n");
                        break outer;
                    }
                    if(i == 9){
                        JOptionPane.showMessageDialog(null,"内存不足，请等待...");
                        break outer;
                    }
                }
            }
        });
        //释放内存按钮监听  
        releaseMemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int m = Integer.parseInt(releaseMemTF.getText());//进程块的起始位置和长度
                for(int ff = m;ff < (m + processBlockLength[m]);ff++){
                    processBlock[ff] = 0;
                }
                processBlockStartAdd[m] = 0;
                processBlockLength[m] = 0;
                colorr();

                showMemStatusTF.setText("");

                for(int bb = 0;bb < 10; bb++){
                    if(processBlockLength[bb] != 0)
                        showMemStatusTF.append("块号： " + processBlock[bb] + "起始位置：" + 
                                processBlockStartAdd[bb] + "大小: "+ processBlockLength[bb] + "\n");
                }
            }
        });
        //清空内存按钮监听
        emptyMemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for(int cc = 0;cc < 10;cc++)
                    processBlock[cc] = 0;
                colorr();

                applyMemTF.setText("");
                releaseMemTF.setText("");
                showMemStatusTF.setText("");
            }
        });
    }

    //判断内存空间是否足够
    public int Sum(int[] pp,int mm,int k){
        int sum = 0;
        if((mm + k) <= 10){
            for(int zz = mm;zz < (mm + k);zz++)
                sum+=pp[zz];
        }
        else {
            sum = 1;
        }

        return sum;
    }

    //内存与processBlock数组相对应,占用颜色为绿色，空白为蓝色    
    public void colorr(){

        if(processBlock[0]==1)
            blank0.setBackground(Color.GREEN);
        else 
            blank0.setBackground(Color.WHITE);

        if(processBlock[1]==1)
            blank1.setBackground(Color.GREEN);
        else 
            blank1.setBackground(Color.WHITE);

        if(processBlock[2]==1)
            blank2.setBackground(Color.GREEN);
        else 
            blank2.setBackground(Color.WHITE);

        if(processBlock[3]==1)
            blank3.setBackground(Color.GREEN);
        else 
            blank3.setBackground(Color.WHITE);

        if(processBlock[4]==1)
            blank4.setBackground(Color.GREEN);
        else 
            blank4.setBackground(Color.WHITE);

        if(processBlock[5]==1)
            blank5.setBackground(Color.GREEN);
        else 
            blank5.setBackground(Color.WHITE);

        if(processBlock[6]==1)
            blank6.setBackground(Color.GREEN);
        else 
            blank6.setBackground(Color.WHITE);

        if(processBlock[7]==1)
            blank7.setBackground(Color.GREEN);
        else 
            blank7.setBackground(Color.WHITE);

        if(processBlock[8]==1)
            blank8.setBackground(Color.GREEN);
        else 
            blank8.setBackground(Color.WHITE);

        if(processBlock[9]==1)
            blank9.setBackground(Color.GREEN);
        else 
            blank9.setBackground(Color.WHITE);
    }

    public static void main(String[] args){
        Memory frame = new Memory();
        frame.setTitle("内存模拟分配与回收——首次适应算法");
        frame.setSize(500,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}