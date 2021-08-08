
public  class LinkedList<T> implements List<T> {

   private Node<T> head;
   private Node<T> current;

   public LinkedList() {
      head = current = null;
   }

   public boolean empty() {
      return head == null;
   }
   
   public boolean full() {
      return false;
   }
   
   public void findFirst() {
      current = head;
   }
   
   public void findNext() {
      current = current.next;
   }
   
   public boolean last() {
      return current.next == null;
   }
   	
   public T retrieve() {
      return current.data;
   }
   
   public void update(T e) {
      current.data = e;
   }
   
   public void insert(T e) {
      Node<T> tmp;
      if (empty()) {
         current = head = new Node<T> (e);
      }
      else {
         tmp = current.next;
         current.next = new Node<T> (e);
         current = current.next;
         current.next = tmp;
      }
   }
   
   public void remove() {
      if (current == head) {
         head = head.next;
      }
      else {
         Node<T> tmp = head;
      
         while (tmp.next != current)
            tmp = tmp.next;
      
         tmp.next = current.next;
      }
   
      if (current.next == null)
         current = head;
      else
         current = current.next;
   }
   
   // Method to print the LinkedList.
   public  void printList()
   {
      Node currNode = head;
   
      System.out.print("LinkedList: ");
   
      // Traverse through the LinkedList
      while (currNode != null) {
         // Print the data at current node
         System.out.print(currNode.data + " ");
      
         // Go to next node
         currNode = currNode.next;
      }
   
      System.out.println();
   }

    @Override
    public boolean first() {
        return current == head;
    }

   
}