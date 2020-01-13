import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PC extends WindowAdapter {//主程序
	public PC(){
	    JFrame f = new JFrame("生产者消费者模拟");
	    Content content = new Content();
		f.add("Center",content);
		Control control = new Control(content);
		f.add("North",control);
		f.setVisible(true);
		f.setSize(600,570);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension frameSize = f.getSize();
		f.setLocation((screenSize.width - frameSize.width) / 2,(screenSize.height - frameSize.height) / 2);//设置主界面显示位置
	    f.addWindowListener(this);                 	
	}
	public void windowClosing(WindowEvent arg0) {
		System.exit(0);
	}
	public static void main (String args[]) {
		new PC();	
	}		
}
class Content extends JPanel{//content是主显示部分
	JTextField tf1,tf2,tf3,tf4,tf5,tf6;
	JTextArea ta;
	public Content(){
		setLayout(null);//布局 
		tf1 = new JTextField();//六个JTextField模仿堆栈
		tf2 = new JTextField();
		tf3 = new JTextField();
		tf4 = new JTextField();
		tf5 = new JTextField();
		tf6 = new JTextField();
		add(tf1);add(tf2);add(tf3);//添加文本框 
		add(tf4);add(tf5);add(tf6);

            int a = 94;
		    int b = 100;
		    int c = 197,d = 45;
			tf1.setBounds(new Rectangle(b,a+225,c,d));//手动设置JTextField的显示位置
			tf2.setBounds(new Rectangle(b,a+180,c,d));
			tf3.setBounds(new Rectangle(b,a+135,c,d));
			tf4.setBounds(new Rectangle(b,a+90,c,d));
			tf5.setBounds(new Rectangle(b,a+45,c,d));
			tf6.setBounds(new Rectangle(b,a,c,d));									

	        tf1.setHorizontalAlignment(JTextField.CENTER); //JTextField中的内容居中显示 
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
		public void showPush(int c,int index){//入栈的界面显示控制
			switch(index){
				case 1:tf1.setText(""+c+"");break;
				case 2:tf2.setText(""+c+"");break;
				case 3:tf3.setText(""+c+"");break;
				case 4:tf4.setText(""+c+"");break;
				case 5:tf5.setText(""+c+"");break;
				case 6:tf6.setText(""+c+"");	
			}
			ta.insert(Thread.currentThread().getName() + "生产："+c+"\n",z);
		}
		
		public void showPop(int c,int index){//出栈的界面显示控制
			switch(index){
				case 1:tf1.setText("");break;
				case 2:tf2.setText("");break;
				case 3:tf3.setText("");break;
				case 4:tf4.setText("");break;
				case 5:tf5.setText("");break;
				case 6:tf6.setText("");	
			}
			ta.insert(Thread.currentThread().getName() + "消费："+c+"\n",z);
		}
		
		public void clear(){//清空JTextArea来进行“重新开始”
			synchronized(SyncStack.class){
			ta.setText("");
			tf1.setText("");tf2.setText("");tf3.setText(""); 
			tf4.setText("");tf5.setText("");tf6.setText(""); 
			Producer.count=1;
			SyncStack.index=0;
			}
		}
		public void full(){
			ta.insert("栈满，生产者wait\n",z);	
		}
		public void empty(){
			ta.insert("栈空，消费者wait\n",z);	
		}
	}
	
class Control extends JPanel implements ActionListener{//control是控制选择的部分
	ButtonGroup xuan = new ButtonGroup();
	JRadioButton one = new JRadioButton("一个生产者一个消费者");
	JRadioButton two = new JRadioButton("多个生产者多个消费者");
	JButton start = new JButton("开始");
	JButton restart = new JButton("重新开始");
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
           public void actionPerformed(ActionEvent e) {//四个组件的监听事件       	
             if (one.isSelected()) {//选择一个生产者一个消费者
             	if (e.getActionCommand()=="开始"){
             		content.clear();
             		go1();
             	}else
             		if (e.getActionCommand()=="重新开始"){
             			content.clear();//清空界面再调用go1()
             			go1();
             		}
             }else 
             	if (two.isSelected()) {//选择多个生产者多个消费者
                	if (e.getActionCommand()=="开始"){
                		content.clear();
             		go2();
             	}else
             		if (e.getActionCommand()=="重新开始"){
             			content.clear();//清空界面再调用go2()
             			go2();
             		}
                }else 
                 System.out.println("start2");
            }
            
            public void go1(){//一个生产者一个消费者的运行程序
		        SyncStack stack = new SyncStack(content);
				Runnable source = new Producer(stack);
				Runnable sink = new Consumer(stack);
				Thread t1 = new Thread(source,"生产者");
				Thread t2 = new Thread(sink,"消费者");
				t1.start();
				t2.start();
            }
            
        public void go2(){//多个生产者多个消费者的运行程序
        SyncStack stack = new SyncStack(content);
		Runnable producer1 = new Producer(stack);
		Runnable producer2 = new Producer(stack);
		//Runnable producer3 = new Producer(stack);
		Runnable consumer1 = new Consumer(stack);
		Runnable consumer2 = new Consumer(stack);
		Runnable consumer3 = new Consumer(stack);
		Runnable consumer4 = new Consumer(stack);
		Thread p1 = new Thread(producer1,"生产者1");
		Thread p2 = new Thread(producer2,"生产者2");
		Thread c1 = new Thread(consumer1,"消费者1");
		Thread c2 = new Thread(consumer2,"消费者2");
		Thread c3 = new Thread(consumer3,"消费者3");
		Thread c4 = new Thread(consumer4,"消费者4");
		p1.start();
		p2.start();
		c1.start();
		c2.start();
		c3.start();
		c4.start();
        }
}
class SyncStack{//共享资源 
	Content theCon;
	public static int index = 0;//堆栈指针初始值为0
	public SyncStack(){}
	public SyncStack(Content c){
		theCon = c;
	}
	private int []buffer = new int[6];//堆栈有6个字符的空间
	public synchronized void push(int c){//加锁进行同步 
		while(index == buffer.length)//堆栈已满，不能压栈
		{
			try{
				this.wait();//等待，直到有数据出栈
				theCon.full();
			}
			catch(InterruptedException e){}
		}
		this.notify();//通知其他线程把数据出栈，唤醒一个wait的线程
		buffer[index] = c;//数据入栈
		index++;//指针向上移动
		theCon.showPush(c,index);//调用界面显示入栈程序
	}

	public synchronized int pop(){
		while(index == 0)//堆栈无数据，不能出栈
		{
			try
			{
				this.wait();//等待其他线程把数据入栈
				theCon.empty();
			}
			catch(InterruptedException e){}
		}
		this.notify();//通知其他线程入栈
		int c = buffer[index-1];
		theCon.showPop(c,index);//调用界面显示出栈程序
		index --;//指针向下移动
		return buffer[index];//数据出栈
	}
}
class Producer implements Runnable{
	SyncStack theStack;//生产者类生成的字母都保存到同步堆栈中
	public static int count=1;
	public Producer(SyncStack s){
		theStack = s;
	}
	public void run(){
		for(int i=0; i<20; i++){
			synchronized(Producer.class){
				theStack.push(count);//入栈
				count++;
				try{
					Thread.sleep((int)(Math.random()*1000));//每产生一个字母后线程就随即睡眠一段时间
				}
				catch(InterruptedException e){}
			}
			
		}
	}
}
class Consumer implements Runnable{
	SyncStack theStack;//消费者类获得的字母都来自同步堆栈
	public Consumer(SyncStack s){
		theStack = s;
	}
	public void run(){
		int c;
		for(int i=0; i<20; i++){
			c = theStack.pop();//从堆栈中读取字母
			try{
				Thread.sleep((int)(Math.random()*10000));//每读取一个字母后线程就随即睡眠一段时间
			}
			catch(InterruptedException e){}
		}
	}
}