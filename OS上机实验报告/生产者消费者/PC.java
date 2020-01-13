import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PC extends WindowAdapter {//������
	public PC(){
	    JFrame f = new JFrame("������������ģ��");
	    Content content = new Content();
		f.add("Center",content);
		Control control = new Control(content);
		f.add("North",control);
		f.setVisible(true);
		f.setSize(600,570);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension frameSize = f.getSize();
		f.setLocation((screenSize.width - frameSize.width) / 2,(screenSize.height - frameSize.height) / 2);//������������ʾλ��
	    f.addWindowListener(this);                 	
	}
	public void windowClosing(WindowEvent arg0) {
		System.exit(0);
	}
	public static void main (String args[]) {
		new PC();	
	}		
}
class Content extends JPanel{//content������ʾ����
	JTextField tf1,tf2,tf3,tf4,tf5,tf6;
	JTextArea ta;
	public Content(){
		setLayout(null);//���� 
		tf1 = new JTextField();//����JTextFieldģ�¶�ջ
		tf2 = new JTextField();
		tf3 = new JTextField();
		tf4 = new JTextField();
		tf5 = new JTextField();
		tf6 = new JTextField();
		add(tf1);add(tf2);add(tf3);//����ı��� 
		add(tf4);add(tf5);add(tf6);

            int a = 94;
		    int b = 100;
		    int c = 197,d = 45;
			tf1.setBounds(new Rectangle(b,a+225,c,d));//�ֶ�����JTextField����ʾλ��
			tf2.setBounds(new Rectangle(b,a+180,c,d));
			tf3.setBounds(new Rectangle(b,a+135,c,d));
			tf4.setBounds(new Rectangle(b,a+90,c,d));
			tf5.setBounds(new Rectangle(b,a+45,c,d));
			tf6.setBounds(new Rectangle(b,a,c,d));									

	        tf1.setHorizontalAlignment(JTextField.CENTER); //JTextField�е����ݾ�����ʾ 
	        tf2.setHorizontalAlignment(JTextField.CENTER);  
	        tf3.setHorizontalAlignment(JTextField.CENTER);  
	        tf4.setHorizontalAlignment(JTextField.CENTER);  
	        tf5.setHorizontalAlignment(JTextField.CENTER);  
	        tf6.setHorizontalAlignment(JTextField.CENTER); 
	        
	        ta = new JTextArea();
	        JScrollPane sp = new JScrollPane(ta);
	        add(sp);
	        sp.setBounds(new Rectangle(380,50,170,370));   
	        ta.setLineWrap(true);
		}
        int z = 0;		
		public void showPush(int c,int index){//��ջ�Ľ�����ʾ����
			switch(index){
				case 1:tf1.setText(""+c+"");break;
				case 2:tf2.setText(""+c+"");break;
				case 3:tf3.setText(""+c+"");break;
				case 4:tf4.setText(""+c+"");break;
				case 5:tf5.setText(""+c+"");break;
				case 6:tf6.setText(""+c+"");	
			}
			ta.insert(Thread.currentThread().getName() + "������"+c+"\n",z);
		}
		
		public void showPop(int c,int index){//��ջ�Ľ�����ʾ����
			switch(index){
				case 1:tf1.setText("");break;
				case 2:tf2.setText("");break;
				case 3:tf3.setText("");break;
				case 4:tf4.setText("");break;
				case 5:tf5.setText("");break;
				case 6:tf6.setText("");	
			}
			ta.insert(Thread.currentThread().getName() + "���ѣ�"+c+"\n",z);
		}
		
		public void clear(){//���JTextArea�����С����¿�ʼ��
			synchronized(SyncStack.class){
			ta.setText("");
			tf1.setText("");tf2.setText("");tf3.setText(""); 
			tf4.setText("");tf5.setText("");tf6.setText(""); 
			Producer.count=1;
			SyncStack.index=0;
			}
		}
		public void full(){
			ta.insert("ջ����������wait\n",z);	
		}
		public void empty(){
			ta.insert("ջ�գ�������wait\n",z);	
		}
	}
	
class Control extends JPanel implements ActionListener{//control�ǿ���ѡ��Ĳ���
	ButtonGroup xuan = new ButtonGroup();
	JRadioButton one = new JRadioButton("һ��������һ��������");
	JRadioButton two = new JRadioButton("��������߶��������");
	JButton start = new JButton("��ʼ");
	JButton restart = new JButton("���¿�ʼ");
	Content content;
	public Control(Content content){
		this.content = content;
		xuan.add(one);
		xuan.add(two);
		add(one);
	    add(two);
	    add(start);
	    add(restart);
	    one.addActionListener(this);
	    two.addActionListener(this);
	    start.addActionListener(this);
	    restart.addActionListener(this);	
	}
           public void actionPerformed(ActionEvent e) {//�ĸ�����ļ����¼�       	
             if (one.isSelected()) {//ѡ��һ��������һ��������
             	if (e.getActionCommand()=="��ʼ"){
             		content.clear();
             		go1();
             	}else
             		if (e.getActionCommand()=="���¿�ʼ"){
             			content.clear();//��ս����ٵ���go1()
             			go1();
             		}
             }else 
             	if (two.isSelected()) {//ѡ���������߶��������
                	if (e.getActionCommand()=="��ʼ"){
                		content.clear();
             		go2();
             	}else
             		if (e.getActionCommand()=="���¿�ʼ"){
             			content.clear();//��ս����ٵ���go2()
             			go2();
             		}
                }else 
                 System.out.println("start2");
            }
            
            public void go1(){//һ��������һ�������ߵ����г���
		        SyncStack stack = new SyncStack(content);
				Runnable source = new Producer(stack);
				Runnable sink = new Consumer(stack);
				Thread t1 = new Thread(source,"������");
				Thread t2 = new Thread(sink,"������");
				t1.start();
				t2.start();
            }
            
        public void go2(){//��������߶�������ߵ����г���
        SyncStack stack = new SyncStack(content);
		Runnable producer1 = new Producer(stack);
		Runnable producer2 = new Producer(stack);
		//Runnable producer3 = new Producer(stack);
		Runnable consumer1 = new Consumer(stack);
		Runnable consumer2 = new Consumer(stack);
		Runnable consumer3 = new Consumer(stack);
		Runnable consumer4 = new Consumer(stack);
		Thread p1 = new Thread(producer1,"������1");
		Thread p2 = new Thread(producer2,"������2");
		Thread c1 = new Thread(consumer1,"������1");
		Thread c2 = new Thread(consumer2,"������2");
		Thread c3 = new Thread(consumer3,"������3");
		Thread c4 = new Thread(consumer4,"������4");
		p1.start();
		p2.start();
		c1.start();
		c2.start();
		c3.start();
		c4.start();
        }
}
class SyncStack{//������Դ 
	Content theCon;
	public static int index = 0;//��ջָ���ʼֵΪ0
	public SyncStack(){}
	public SyncStack(Content c){
		theCon = c;
	}
	private int []buffer = new int[6];//��ջ��6���ַ��Ŀռ�
	public synchronized void push(int c){//��������ͬ�� 
		while(index == buffer.length)//��ջ����������ѹջ
		{
			try{
				this.wait();//�ȴ���ֱ�������ݳ�ջ
				theCon.full();
			}
			catch(InterruptedException e){}
		}
		this.notify();//֪ͨ�����̰߳����ݳ�ջ������һ��wait���߳�
		buffer[index] = c;//������ջ
		index++;//ָ�������ƶ�
		theCon.showPush(c,index);//���ý�����ʾ��ջ����
	}

	public synchronized int pop(){
		while(index == 0)//��ջ�����ݣ����ܳ�ջ
		{
			try
			{
				this.wait();//�ȴ������̰߳�������ջ
				theCon.empty();
			}
			catch(InterruptedException e){}
		}
		this.notify();//֪ͨ�����߳���ջ
		int c = buffer[index-1];
		theCon.showPop(c,index);//���ý�����ʾ��ջ����
		index --;//ָ�������ƶ�
		return buffer[index];//���ݳ�ջ
	}
}
class Producer implements Runnable{
	SyncStack theStack;//�����������ɵ���ĸ�����浽ͬ����ջ��
	public static int count=1;
	public Producer(SyncStack s){
		theStack = s;
	}
	public void run(){
		for(int i=0; i<20; i++){
			synchronized(Producer.class){
				theStack.push(count);//��ջ
				count++;
				try{
					Thread.sleep((int)(Math.random()*1000));//ÿ����һ����ĸ���߳̾��漴˯��һ��ʱ��
				}
				catch(InterruptedException e){}
			}
			
		}
	}
}
class Consumer implements Runnable{
	SyncStack theStack;//���������õ���ĸ������ͬ����ջ
	public Consumer(SyncStack s){
		theStack = s;
	}
	public void run(){
		int c;
		for(int i=0; i<20; i++){
			c = theStack.pop();//�Ӷ�ջ�ж�ȡ��ĸ
			try{
				Thread.sleep((int)(Math.random()*10000));//ÿ��ȡһ����ĸ���߳̾��漴˯��һ��ʱ��
			}
			catch(InterruptedException e){}
		}
	}
}