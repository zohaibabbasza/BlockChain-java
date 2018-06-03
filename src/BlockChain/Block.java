/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

/**
 *
 * @author ZohaibAbbasZA
 */
import java.util.Date;
import java.security.MessageDigest;

class Block
{
	public String hash;
	public String previousHash;
	public static Block lastBlock;
	public String receiverName;
	public String senderName;
	public String amount;
	public long time;
	public Block prev;
	private Block next;
	private static int diff =1;
	private int miner;
	public Block()
	{}
	public Block(String sName,String rName,String amount)
	{
		this.senderName = sName;
		receiverName =rName;
		this.amount = amount;
		this.previousHash = null;
		this.time = new Date().getTime();
		this.hash=calculateHash();
		prev = null;
		next = null;
	}
      	public Block(String sName,String rName,String amount,String timef,String prevHash)
	{
           long time= Long.parseLong(timef);
		this.senderName = sName;
		receiverName =rName;
		this.amount = amount;
		this.previousHash = prevHash;
		this.time = time;
		this.hash=calculateHash();
		prev = null;
		next = null;
	}
	public static class StringUtil { 
	public static String applySha256(String input){		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			StringBuffer hexString = new StringBuffer(); 
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	}
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				Long.toString(time) +
				senderName+receiverName+amount+miner
				);
		return calculatedhash;
	}

	public long length()
	{
		long i = 0 ;
		Block n = lastBlock;
		while(n.prev != null)
		{
			i++;
			n=n.prev;
		}
		return i;
	}	
	public boolean check()
	{
       
                Block n = lastBlock;
		if(n == null)
		{
		  return true;
		}
                if(n.previousHash == "null")
                {
                    return true;
                }
                   int i = 0;   
		while(n.prev != null )
		{
                    i++;
     
                   if(!n.previousHash.equals(n.prev.hash))
                    {
                        System.out.println(i);
                        return false;
                    }
                    
                    
                    n=n.prev;
		}
	
                
		return true;
			
	}

	public void mining()
	{
		
			this.hash=calculateHash();
			String mine = new String(new char[diff]).replace('\0','0');
			while(!this.hash.substring(0,diff).equals(mine))
			{
				miner++;
				this.hash=calculateHash();
			}
			


	}
	public void createBlock(String sName,String rName,String amount)
	{
		Block n = new Block(sName,rName,amount);
		n.mining();
		Block nn = lastBlock;
		if(nn == null)
		{
			lastBlock = n;
		}
		else
		{
		while(nn.next != null)
		{
			nn=nn.next;
		}
		
		nn.next=n;
		lastBlock = n;
		nn.next.previousHash=nn.hash;
		nn.next.prev=nn;
		}
	}
        public void createBlock(String sName,String rName,String amount,String time,String prevHash)
	{
		Block n = new Block(sName,rName,amount,time,prevHash);
		
		Block nn = lastBlock;
		if(nn == null)
		{
			lastBlock = n;
		}
		else
		{
		while(nn.next != null)
		{
			nn=nn.next;
		}
		
		nn.next=n;
		lastBlock = n;
		nn.next.prev=nn;
		}
                n.mining();
	}

	public void printCheck()
	{
		Block n = lastBlock;
		while(n != null)
		{
			System.out.println(n.senderName+" "+n.hash+" "+n.previousHash);
			n=n.prev;
		}
	}
}
