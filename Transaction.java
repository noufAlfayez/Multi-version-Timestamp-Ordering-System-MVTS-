
public class Transaction {
    
    String status;
    int timestamp;
    int name;
    
 
   
   // initialize tansaction 
     public  Transaction(){
        timestamp = -1;
        status = "begain";
     }
    public  Transaction(int name, String s, int ts){
       timestamp = ts;
       status = s;
    }
    
    public String getStatus() {
      return status;
   }

   public void setStatus(String s) {
      status = s;
   }

   public int getTimestamp() {
      return timestamp;
   }

   public void settimestamp(int ts) {
      timestamp = ts;
   } 
   
   public int getName() {
      return name;
   }

   public void setName(int n) {
      name = n;
   } 
}
