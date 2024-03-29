var quantum = 0;
var Total_Process = 0;
var Process = [];
var i=0;
var HRRN = {
    Pro_Ins : 0,
    Pro_Name : '',
    Pro_PC: 0,
    Pro_Completion:false,
    Pro_Arrival:0,
    HRRN_val:0,
    Pro_color:'',
    construct(Process_instruction,Process_Name,Process_color){
        this.Pro_Ins = Process_instruction;
        this.Pro_Name = Process_Name;
        this.Pro_color = Process_color
    },
    set(i){
    this.Pro_Arrival = document.getElementById("Arrival_Input"+i).value;
    appendProcess_Table2(i);
},
    setIns(i){
        this.Pro_Ins = document.getElementById("getIns"+i).value;
    }
};

function Setter(i){
    Process[i].setIns(i);
    Process[i].set(i);
}

function getProcess(){
    Total_Process = document.getElementById("Pro_Num").value;
}

function appendProcess(){
    document.getElementById("Table_Scroll").style.visibility="visible";
    for(var i=0;i<Total_Process;i++){
        Process[i] = Object.create(HRRN);
        var randomColor = Math.floor(Math.random()*16777215).toString(16);
        Process[i].construct(0,"Processs "+(i+1),randomColor);
        var row = document.createElement("tr");
        var Process_column1 = document.createElement("td");
        var Process_column2 = document.createElement("td");
        var Process_column3 = document.createElement("td");
        var Process_column4 = document.createElement("td");
        var Process_button = document.createElement("button");
        var Process_Input_Field = document.createElement("input");
        Process_Input_Field.setAttribute("type","number");
        Process_Input_Field.id = "Arrival_Input"+i;
        var Process_Instruction_Input = document.createElement("input");
        Process_Instruction_Input.setAttribute("type","number");
        Process_Instruction_Input.id="getIns"+i;
        Process_button.setAttribute("onclick","Setter("+i+")");
        Process_button.className="btn";
        var buttonText = document.createTextNode("Set");//for arrival
        var Process_Name_Column = document.createElement("p");
        Process_Name_Column.innerHTML = Process[i].Pro_Name;
        Process_column1.appendChild(Process_Name_Column);
        Process_column1.className = "Process_Table_Data";
        Process_column2.className = "Process_Table_Data";
        Process_column3.className = "Process_Table_Data";
        Process_column4.className = "Process_Table_Data";
        Process_column2.appendChild(Process_Instruction_Input);
        Process_column3.appendChild(Process_Input_Field);
        Process_column4.appendChild(Process_button);
        Process_button.appendChild(buttonText);
        row.appendChild(Process_column1);
        row.appendChild(Process_column2);
        row.appendChild(Process_column3);
        row.appendChild(Process_column4);
        document.getElementById("Process_Table").appendChild(row);
    }
    
}

function createBlock(i){
    var block = document.createElement("p");
    block.className="Block";
    block.style.backgroundColor="#"+Process[i].Pro_color;
    //appending

    var parent = document.getElementById("Processes");
    var text = document.createTextNode(Process[i].Pro_Name);
    block.appendChild(text);
    parent.appendChild(block);
}

function Instantiate(){
    getProcess();
    appendProcess();
}

function Start(){
    document.getElementById("Table_Scroll").style.height = "0px";
    document.getElementById("Table_Scroll").style.visibility = "hidden";
    document.getElementById("Detail_Table").style.visibility = "visible";

    setInterval(frame,1000);
}

function frame(){
    if(Process[i].Pro_PC<Process[i].Pro_Ins){
        createBlock(i);
        Process[i].Pro_PC++;
        document.getElementById("Edit_Pro_PC"+i).innerHTML = Process[i].Pro_PC;
        document.getElementById("Edit_Pro_Rem"+i).innerHTML = Process[i].Pro_Ins-Process[i].Pro_PC;
        quantum++;
    }
    else{
        Process[i].Pro_Completion=true;
        var HRRN_algo=[];
        var index=-1;
        for(var j=0;j<Total_Process;j++){
            if(Process[j].Pro_Completion==false){
        var waitingTime = parseInt(quantum)-parseInt(Process[j].Pro_Arrival);
        Process[j].HRRN_val = parseInt(parseInt(waitingTime)+parseInt(Process[j].Pro_Ins))/parseInt(Process[j].Pro_Ins);
        document.getElementById("Edit_HRRN_val"+j).innerHTML = Process[j].HRRN_val;
        HRRN_algo[++index]=Process[j].HRRN_val;
            }
        }
        HRRN_algo.sort(function(a, b){return a-b});
        for(var g=0;g<Total_Process; g++){  
        if(Process[g].HRRN_val==HRRN_algo[index]&&Process[g].Pro_Completion==false){
            i=g;
            break;
        }
        }
    }
}
function appendProcess_Table2(i){
    var table = document.getElementById("Process_Table2");
    
    var Process_column1 = document.createElement("td");
    var text = document.createTextNode(Process[i].Pro_Name);
    Process_column1.appendChild(text);    
    
    var Process_column2 = document.createElement("td");
    var text = document.createTextNode(Process[i].Pro_Ins);
    Process_column2.appendChild(text);    
    
    var Process_column3 = document.createElement("td");
    var text = document.createTextNode(Process[i].Pro_Arrival);
    Process_column3.appendChild(text);
    
    var Process_column4 = document.createElement("td");
    var text = document.createTextNode(Process[i].Pro_PC);
    Process_column4.appendChild(text);
    Process_column4.id = "Edit_Pro_PC"+i;

    var Process_column5 = document.createElement("td");
    var text = document.createTextNode(Process[i].Pro_Ins-Process[i].Pro_PC);
    Process_column5.appendChild(text);
    Process_column5.id = "Edit_Pro_Rem"+i;
	
    var waitingTime = parseInt(quantum-Process[i].Pro_Arrival);
    Process[i].HRRN_val = parseInt((waitingTime+parseInt(Process[i].Pro_Ins)))/parseInt(Process[i].Pro_Ins);

    var Process_column6 = document.createElement("td");
    var text = document.createTextNode(Process[i].HRRN_val);
    Process_column6.appendChild(text);
    Process_column6.id = "Edit_HRRN_val"+i;
    var row = document.createElement("tr");
    row.appendChild(Process_column1);
    row.appendChild(Process_column2);
    row.appendChild(Process_column3);
    row.appendChild(Process_column4);
    row.appendChild(Process_column5);
    row.appendChild(Process_column6);
    table.appendChild(row);
}