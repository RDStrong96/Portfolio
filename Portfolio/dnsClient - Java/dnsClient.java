//Author: Ryan Strong - 7764341
//Comp 4300
//Instructor: Peter Graham
//October 2019

import java.net.*;
import java.io.*;

public class dnsClient{
  
  public static void main(String[] args)
  {
    String input = null;
    String[] sections = null;
    String response;
    InputStreamReader instrm = null; // terminal input stream
    BufferedReader stdin = null;     // buffered version of instrm
 
    DatagramSocket socket = null; //The socket to send the packet
    DatagramPacket pack = null; //The packet to be sent
    InetAddress addr = null;
    byte[] buf = null;
    byte[] tempArray = null;
    byte[] message = null;
    int tempByte;
    int ipCount = 0;
    ByteArrayOutputStream byteout = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(byteout);
    //Assign address
    try{
      addr = InetAddress.getByName("8.8.8.8");
    }catch(Exception e){
      System.out.println("Assigning address failed!");
      System.out.println("Error code: "+e);
      System.exit(1);
    }
    //Create command line reader
    try{
      instrm = new InputStreamReader(System.in);
      stdin = new BufferedReader(instrm);
    }catch(Exception e){
      System.out.println("Creating command line reader failed!");
      System.out.println("Error code: "+e);
      System.exit(1);
    }
    //Make a header to be used in each querry. These fields can be changed if needed.
    int ID = 0x4341; //Can be anything, but I like these numbers
    int FLAGS = 0x0100; //We only want recursion desired from the flags, bit 7
    int QDCOUNT = 0x0001; //We will only have 1 querry
    int ANCOUNT = 0x0000; //None
    int NSCOUNT = 0x0000; //None
    int ARCOUNT = 0x0000; //None
// create datagram socket 
    try {
      socket = new DatagramSocket(44444);
    } catch (Exception e) {
      System.out.println("Creation of socket failed.");
      System.out.println("Error code: "+e);
      System.exit(1);
    }
    //Asemble the header
    try{
      out.flush(); //New message
      out.writeShort(ID);
      out.writeShort(FLAGS);
      out.writeShort(QDCOUNT);
      out.writeShort(ANCOUNT);
      out.writeShort(NSCOUNT);
      out.writeShort(ARCOUNT);
    }catch(Exception e){
      System.out.println("Creating header failed!");
      System.out.println("Error code: "+e);
      System.exit(1);
    }
    
    try{
      if (args.length == 0){
        input = stdin.readLine(); //Get name from user
      }else{
        input = args[0]; //Get name from command line
      }
      sections = input.split("\\."); //Split by "."
      for (int i = 0; i < sections.length; i++)
      {
        tempArray = sections[i].getBytes("UTF-8");
        out.writeByte(tempArray.length);
        out.write(tempArray);
      }
      //End of labels
      out.writeByte(0x00);
      //End of question declarations
      out.writeShort(0x0001);
      out.writeShort(0x0001);
    }catch(Exception e){
      System.out.println("Line reader/writer failed!");
      System.out.println("Error code: "+e);
      System.exit(1);
    }
    
    
    //create the byte array message to be sent
    message = byteout.toByteArray();
    //Create and send new packet for output
    try{
      pack = new DatagramPacket(message, message.length, addr,53);
      socket.send(pack);
    }catch (Exception e) {
      System.out.println("Error, output packet not created!");
      System.out.println("Error code: "+e);
      System.exit(1);
    }
    //Create new buf and packet for response from server
    try{
      buf = new byte[1024];
      pack = new DatagramPacket(buf, buf.length);
    }catch (Exception e) {
      System.out.println("Error, input packet not created!");
      System.out.println("Error code: "+e);
      System.exit(1);
    }
    //Receive message from server
    try{
      socket.receive(pack);
      response = new String(pack.getData(),0,pack.getLength());
    }catch (Exception e){
      System.out.println("Error, can't read from server!");
      System.out.println("Error code: "+e);
      System.exit(1);
    }
    System.out.println("The response was "+pack.getLength() + " bytes long");
    DataInputStream in = new DataInputStream(new ByteArrayInputStream(buf)); //Read from the buffer
    //split into an array of bytes
    byte[] resBytes = new byte[pack.getLength()];
    try{
      for (int x = 0;x < pack.getLength(); x++)
      {
        resBytes[x] = in.readByte();
      }
    }catch(Exception e){
      System.out.println("Error, response reading failed!");
      System.out.println("Error code: "+e);
      System.exit(1);
    }
    
    if ((resBytes[3] & 128) == 128){
      System.out.println("Recursion is allowed!");
    }else{
      System.out.println("Recursion is not allowed!");
    }
    System.out.println("The response code: "+ (resBytes[3]&15));
    tempByte = resBytes[7]+(resBytes[6]*256);
    System.out.println("Answer count: "+ tempByte);
    
    int cursor = 12; //The first byte of the question section
    while (resBytes[cursor] != 0) //So long as we still have labels
    {
      cursor += (resBytes[cursor]+1); //Jump over this label
    }
    //The cursor is now past the QName section
    cursor += 5; //Jump past QType and QCLass
    //cursor now at the start of the Resource section
    System.out.println("The following IP addresses were found:");
    do{
      if (resBytes[cursor]>0){ //A label based name convention was returned
        while (resBytes[cursor] != 0) //So long as we still have labels
        {
          cursor += (resBytes[cursor]+1); //Jump over this label
        }
      }else{//A pointer based name was returned
        cursor += 2;
      }
      //Past RNAME
      cursor++;
      //2nd byte of Type
      if ((resBytes[cursor] == 1) || (resBytes[cursor] == 28) )
      {
        //Either an A or AAAA type, we have an IP address!
        ipCount++;
        cursor += 7;
        //skip CLASS and TTL
        tempByte = resBytes[cursor+1]+(resBytes[cursor]*256); //full value of RDLENGTH
        cursor += 2;
        //In RDATA
        for (int q = 0; q < tempByte; q++)
        {
          System.out.print(String.format("%d", (resBytes[cursor] & 0xFF))+".");
          cursor++;
        }
        System.out.print("\n");
      }else{
        //Not IP, skip
        cursor += 7;
        //skip CLASS and TTL
        tempByte = resBytes[cursor+1]+(resBytes[cursor]*256);
        cursor += 2 + tempByte;
      }
    }while(cursor < resBytes.length);
    System.out.println("We found " + ipCount + " IP address(es) total");
    socket.close();
  }
}
