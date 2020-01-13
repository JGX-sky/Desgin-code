#include<bits/stdc++.h>
#include<windows.h>
using namespace std;
const int maxn=1000;
int ax[maxn];
struct node{
	int ax[3];
	int size;
	int count;
	void move(){
		for(int i=1;i<3;i++)
		ax[i-1]=ax[i];
	}
};
void clear_screen(){
	system("cls");
}
int main(){
	int n;
	while(1){
		cout<<"请输入待访问页面页数："<<endl;
		cin>>n; 
		cout<<"请输入各个页面号："<<endl;
		for(int i=0;i<n;i++){
			cin>>ax[i];
		} 
		node a;
		a.size=0;
		a.count=0;
		for(int i=0;i<4;i++)
		a.ax[i]=0;
		for(int i=0;i<n;i++){
			clear_screen();
			cout<<"页面号"<<endl;
			for(int c=0;c<n;c++){
				cout<<ax[c]<<" ";
			} 
			cout<<endl;
			for(int j=0;j<=i;j++){
				if(j==i){
					cout<<"↑"<<endl;
					break;
				}
				cout<<"  ";
			}
			//判断是否中断； 
				int flag=0;
				for(int c=0;c<a.size;c++){
					if(a.ax[c]==ax[i]){
						flag=1;
						break;
					}
				}
				if(!flag){
					cout<<"页面"<<ax[i]<<"不在物理块内，"; 
					cout<<"缺页中断"<<endl;
					if(a.size==3){
						a.move();
						a.ax[a.size-1]=ax[i];
					}else{
						a.ax[a.size]=ax[i];
						a.size+=1;
					}
					a.count++;
				}else{
					cout<<"页面"<<ax[i]<<"访问成功！"<<endl;
				} 
			cout<<"物理块内容： ";
			for(int i=0;i<a.size;i++){
				cout<<a.ax[i]<<" ";
			}
			cout<<endl;
			Sleep(2000);
		}
		cout<<"缺页次数："<<a.count<<endl;
		cout<<"缺页率："<<a.count*1.0/n*100<<"%"<<endl;; 
		int ch;cin>>ch;
		clear_screen();
	} 
	return 0;
}
