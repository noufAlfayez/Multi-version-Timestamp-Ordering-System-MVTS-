
import static java.lang.Integer.max;


public class MultiVersion {
    
    // we put list for abort transactio in main 
   // List<Transaction>  abort = new LinkedList();
    int v= 0;
    List <tObj> Ilist = new LinkedList();
    
    
    public boolean AddItem( Transaction T ,char n){// n== name of item
        // check if item 'n' already exist in Ilist we will return false 
        
        if(!Ilist.empty()){
             Ilist.findFirst();
             while(!Ilist.last()){
                  if(Ilist.retrieve().name==n) { 
                          return false;
                   } 
                  Ilist.findNext();
             }   
        }
        // if item 'n' does not exist in Ilist we will add it, and return true.
        LinkedList vl = new LinkedList();
        vl.insert(new ItemVersion(T.timestamp,T.timestamp));
        Ilist.insert(new tObj(n,vl));
        
        return true;
    }
    
    
//+--------------------------------------------------------------------------------------------------------------------------------------------------------+
    //1- looking for the correct version ( TS(T) > Write_ts for value ).
    //2- We compare between TS(T) and Read_ts for the item, then we assign largest value  to Read_ts.
    //In the process of reading it can not be there abortion for any transaction.
public void read( Transaction t, char name){
    AddItem( t, name);
    tObj item;
    if(!Ilist.empty()){
        Ilist.findFirst();
        while(!Ilist.last()){
            item = Ilist.retrieve();

                if(item.name == name ) { // to find item
                    item.VL.findFirst();
                    while(!item.VL.last()){
                        if( t.timestamp >= item.VL.retrieve().WTS){ // find correct virsion
                               item.VL.retrieve().setRTS(max(t.timestamp,item.VL.retrieve().RTS));
                            }

                    item.VL.findNext();
            }
                  if( t.timestamp >= item.VL.retrieve().WTS){ // find correct virsion
                               item.VL.retrieve().setRTS(max(t.timestamp,item.VL.retrieve().RTS));
                            }  
                    
        }
          Ilist.findNext();
        }
        item = Ilist.retrieve();

                if(item.name == name ) { // to find item
                    item.VL.findFirst();
                    while(!item.VL.last()){
                        if( t.timestamp >= item.VL.retrieve().WTS){ // find correct virsion
                               item.VL.retrieve().setRTS(max(t.timestamp,item.VL.retrieve().RTS));
                            }

                    item.VL.findNext();
            }
                  if( t.timestamp >= item.VL.retrieve().WTS){ // find correct virsion
                               item.VL.retrieve().setRTS(max(t.timestamp,item.VL.retrieve().RTS));
                            }  
                    
        }
    }

}


    
    
 //+--------------------------------------------------------------------------------------------------------------------------------------------------------+
    //1- looking for the correct version ( TS(T) > Write_ts ).
    //2- check Read_ts if ( TS(T) < Read_ts), we aborted Transaction, and add this transaction to abort list.
    //Read_ts if ( TS(T) >= Read_ts), create new version for item and set (RTS=WTS=TS(T)).
    //if abort transaction we but status = Abort and return false
    public boolean write(Transaction t, char name){
      
     AddItem(t, name); // to check if item is add in Ilist
     tObj item;
      if(!Ilist.empty()){
            Ilist.findFirst();
         while(!Ilist.last()){
                item = Ilist.retrieve();
      
                 if(item.name == name ) {    // to find item
                     item.VL.findFirst();
                     while(!item.VL.last()){
                            if( t.timestamp >= item.VL.retrieve().WTS){ // to find correct virsion
                                  if(t.timestamp < item.VL.retrieve().RTS){ //if ( TS(T) < Read_ts), we aborted Transaction add this transaction to abort list. 
                                       t.setStatus("Abort");// aborted Transaction
                                        System.out.println("write operation is aborted");
                                       System.out.println(t.status);
                                       return false; // because we abort transaction
                                     }
                                  else{ //if ( TS(T) >= Read_ts), create new version for item and set (RTS=WTS=TS(T)).
                                        ItemVersion NewV = new ItemVersion(t.timestamp, t.timestamp);//create new version for item
                                        item.VL.insert(NewV);
                                        System.out.println("write operation is successfully: T ts is: "+t.timestamp+" item RTS: "+NewV.RTS+" item WTS"+NewV.WTS);
                                         return true;
                                        }   
                                }
                     
                             item.VL.findNext();
                        } 
                     // to check the last item in item.VL.last()
                     if( t.timestamp >= item.VL.retrieve().WTS){ // to find correct virsion
                                  if(t.timestamp < item.VL.retrieve().RTS){ //if ( TS(T) < Read_ts), we aborted Transaction add this transaction to abort list. 
                                       t.setStatus("Abort");// aborted Transaction
                                        System.out.println("write operation is aborted");
                                        System.out.print(t.status);
                                       return false; // because we abort transaction
                                     }
                                  else{ //if ( TS(T) >= Read_ts), create new version for item and set (RTS=WTS=TS(T)).
                                        ItemVersion NewV = new ItemVersion(t.timestamp, t.timestamp);//create new version for item
                                        item.VL.insert(NewV);
                                        System.out.println("write operation is successfully: T ts is: "+t.timestamp+" item RTS: "+NewV.RTS+" item WTS"+NewV.WTS);
                                         return true;
                                        }   
                           }
            } 
                  Ilist.findNext();
            } 
         
         
         // To check the last item in Ilist
         if(Ilist.retrieve().name == name ) {    // to find item
                    Ilist.retrieve().VL.findFirst();
                     while(!Ilist.retrieve().VL.last()){
                            if( t.timestamp >= Ilist.retrieve().VL.retrieve().WTS){ // to find correct virsion
                                  if(t.timestamp < Ilist.retrieve().VL.retrieve().RTS){ //if ( TS(T) < Read_ts), we aborted Transaction add this transaction to abort list. 
                                       t.setStatus("Abort");// aborted Transaction
                                        System.out.println("write operation is aborted");
                                       return false; // because we abort transaction
                                     }
                                  else{ //if ( TS(T) >= Read_ts), create new version for item and set (RTS=WTS=TS(T)).
                                        ItemVersion NewV = new ItemVersion( t.timestamp, t.timestamp);//create new version for item
                                        Ilist.retrieve().VL.insert(NewV);
                                        System.out.println("write operation is successfully: T ts is: "+t.timestamp+" item RTS: "+NewV.RTS+" item WTS"+NewV.WTS);
                                         return true;
                                        }   
                                }
                     
                             Ilist.retrieve().VL.findNext();
                        } 
                     // to check the last item in item.VL.last()
                     if( t.timestamp >= Ilist.retrieve().VL.retrieve().WTS){ // to find correct virsion
                                  if(t.timestamp < Ilist.retrieve().VL.retrieve().RTS){ //if ( TS(T) < Read_ts), we aborted Transaction add this transaction to abort list. 
                                       t.setStatus("Abort");// aborted Transaction
                                       System.out.println("write operation is aborted");
                                       return false; // because we abort transaction
                                     }
                                  else{ //if ( TS(T) >= Read_ts), create new version for item and set (RTS=WTS=TS(T)).
                                        ItemVersion NewV = new ItemVersion( t.timestamp, t.timestamp);//create new version for item
                                        Ilist.retrieve().VL.insert(NewV);
                                        System.out.println("write operation is successfully: T ts is: "+t.timestamp+" item RTS: "+NewV.RTS+" item WTS"+NewV.WTS);
                                         return true;
                                        }   
                           }
            }
       }
        return false;
    }
  //+--------------------------------------------------------------------------------------------------------------------------------------------------------+  

   
}
