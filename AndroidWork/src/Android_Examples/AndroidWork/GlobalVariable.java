package Android_Examples.AndroidWork;

import android.app.Application;
public class GlobalVariable extends Application {
 public String UserID = "��_�гX��";
 
 public String getGlobalVariable() {  
     return UserID;  
 }  
 public void setGlobalVariable(String globalVariable) {  
     this.UserID = globalVariable;  
 }  
 
}
