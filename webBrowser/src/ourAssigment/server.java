package ourAssigment;
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
 
public class server {
	public static void main(String[] args) throws IOException {
 
 
		soal_all();
 
 
	}
 
	public void single_client() {
		ServerSocket server;
		try {
			server = new ServerSocket(6666);
			System.out.println("1");
			Socket client=server.accept();
			System.out.println("2");
 
			BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			System.out.println("3");
			String message=br.readLine();
			System.out.println("4");
			System.out.println("From client: "+message);
			bw.write(message);
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
	}
 
	public static void multiple_clinet() {
		InetAddress addr=(InetAddress) InetAddress.getLoopbackAddress();
		ServerSocket server;
		try {
			server = new ServerSocket(6666,5,addr);
			System.out.println("1");
			while(true) {
				Socket client=server.accept();
				System.out.println("2");
 
				BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				System.out.println("3");
				String message=br.readLine();
				System.out.println("4");
				System.out.println("From client: "+message);
				bw.write(message);
				bw.flush();	
				client.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
	}
 
	public static void simple_http() {
		try {
			ServerSocket server=new ServerSocket(80);
			System.out.println("1");
			while(true) {
				Socket client=server.accept();
				System.out.println("2");
 
				BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				System.out.println("3");
				String message=br.readLine();
				while(!message.isEmpty()) {
					System.out.println(message);
					message=br.readLine();
				}
				System.out.println("4");
				System.out.println("From client: "+message);
				bw.write("hello world");
				bw.flush();	
				client.close();
			}	
		}
		catch(IOException ex) {
//			Logger.getLogger(server.getName()).log(Level.SEVERE,null,ex);
		}
	}

	public static void simple_http2() {
		try {
			String websiteRoot="D:\\";
			ServerSocket server=new ServerSocket(80);
			System.out.println("1");
			while(true) {
				Socket client=server.accept();
				System.out.println("2");
 
				BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				System.out.println("3");
				String message=br.readLine();
				System.out.println(message);
				String urn=message.split(" ")[1];
				
				urn=urn.substring(1);
				System.out.println(websiteRoot+urn);
 
				FileInputStream fis=new FileInputStream(websiteRoot+urn);
				String fileContent=new String(fis.readAllBytes());
				System.out.println(fileContent);
 
 
				while(!message.isEmpty()) {
					System.out.println(message);
					message=br.readLine();
				}
				System.out.println("4");
				System.out.println("From client: "+message);
				String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
 
				bw.write(inputan);
				bw.flush();	
				client.close();
			}	
		}
		catch(IOException ex) {
//			Logger.getLogger(server.getName()).log(Level.SEVERE,null,ex);
		}
	}
	
	
	public static void soal_all() {
		int count=0;
		String pertama=null;
		String folderlisttmp=null;
		try {
 
//			no 4
			String websiteRoot="D:\\";
			FileInputStream config=new FileInputStream("D:\\serverku\\config.txt");
			String configText=new String(config.readAllBytes());
			configText=configText.replaceAll("[\r\n]+", " ");
			String[] configArr=configText.split(" ");
			System.out.println(configArr[5]);
 
			InetAddress addr=InetAddress.getByName(configArr[1]);
//			InetAddress addr=(InetAddress) InetAddress.getLoopbackAddress();
			ServerSocket server=new ServerSocket(Integer.valueOf(configArr[3]),5,addr);
			System.out.println("1");
			String message;
			while(true) {
				Socket client=server.accept();
				System.out.println("2");
 
				BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				System.out.println("3");
				message=br.readLine();
				System.out.println("the message:"+message);
				String url=br.readLine();
				System.out.println("the url:"+url);
 
//				no5
				count++;
				if(count==1) {
					pertama=url.split(" ")[1];
	//				System.out.println("url: pertama"+pertama);
				}
//				else {
//					if(url.indexOf(pertama)<0)client.close();continue;
//				}
 
				if(message.isEmpty()==true)continue;
				if(message.indexOf(".ico")>=0)continue;
 
				if(message.indexOf(".pdf")>=0||message.indexOf(".jpg")>=0) {
//					no 1
					String filetmp=message.split(" ")[1];
					filetmp=filetmp.substring(1);
 
//					no 3
					if(url.indexOf("progjarku.com")>=0)websiteRoot=configArr[5];
					else if(url.indexOf("progjarkutercinta.com")>=0)websiteRoot=configArr[7];
 
 
					FileInputStream newfis;
 
					System.out.println("website lengkap:["+websiteRoot+filetmp+"]");
					newfis=new FileInputStream(websiteRoot+filetmp);
 
					String newfileContent=new String(newfis.readAllBytes());
//					System.out.println(newfileContent);
 
					File f = new File("D:\\serverku\\File\\"+filetmp);
		            BufferedWriter gambar = new BufferedWriter(new FileWriter(f));
 
		            bw.write(folderlisttmp);
//		            System.out.println(newfileContent);
		            bw.flush();
//		            client.close();
		            System.out.println("berhasil 3");
					continue;
				}
				else if(message.split(" ")[1].substring(1).indexOf(".")<0) {
 
//					no 2
					String baru = null,tmp=null;
					try {
 
						  String dirLocation=null;
						  String urlafter=url.split(" ")[1];
//						  no 3
						  if(urlafter.indexOf("progjarku.com")>=0)dirLocation=configArr[5];
						  else if(urlafter.indexOf("progjarkutercinta.com")>=0)dirLocation=configArr[7];
						  System.out.println("dirlocaction1: "+dirLocation);
						  if(!Files.exists(Paths.get(dirLocation)))continue;
					      List<File> myfile = Files.list(Paths.get(dirLocation))
					            .map(Path::toFile)
					            .collect(Collectors.toList());
 
					      for(int i=0;i<myfile.size();i++) {
					    	  tmp=myfile.get(i).toString();
//					    	  System.out.println(tmp);
					    	  tmp=tmp.replaceAll("\\\\", " ");
					    	  String[] listtmp=tmp.split(" ");
					    	  tmp=listtmp[listtmp.length-1];
					    	  System.out.println(tmp);
					    	  baru="<a href=\"/" + tmp + "\">"+tmp+"</a><br>"+baru;
					      }
 
					      if(myfile.size()<=0)continue;
 
 
					} catch (IOException e) {
					      // Error while reading the directory
					}
 
 
//					baru="<a href=\"/" + "index.html" + "\">cek</a><br>";
					String fileContent=new String(baru);
					String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
//					System.out.println(inputan);
					folderlisttmp=inputan;
					bw.write(inputan);
					bw.flush();	
					System.out.println("4");
//					client.close();
//					continue;
 
				}
				else if(message.indexOf("html")<0) {
					System.out.println(message);
					System.out.println("kosong");
//					continue;
				}
				else {
 
					String urn=message.split(" ")[1];
 
	//				no 5
				    if(url.indexOf(".com")>=0) {
						if(count>=1) {
							if(url.indexOf(pertama)<0)continue;
 
						}
 
					}
 
	//				no 3
					if(url.indexOf("progjarku.com")>=0)websiteRoot=configArr[5];
					else if(url.indexOf("progjarkutercinta.com")>=0)websiteRoot=configArr[7];
 
					urn=urn.substring(1);
					System.out.println("dirnya"+websiteRoot+urn);
 
 
					FileInputStream fis=new FileInputStream(websiteRoot+urn);
					String fileContent=new String(fis.readAllBytes());
					System.out.println(fileContent);
 
 
					while(!message.isEmpty()) {
						System.out.println(message);
						message=br.readLine();
					}
//					System.out.println("4");
	//				System.out.println("From client: "+message);
					String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
					folderlisttmp=inputan;
					bw.write(inputan);
					bw.flush();	
//					client.close();
 
 
 
 
				}
				System.out.println("4");
//				client.close();
 
 
			}	
		}
		catch(IOException ex) {
//			Logger.getLogger(server.getName()).log(Level.SEVERE,null,ex);
		}
	}

	
	
	public static void soal_1() {
		int count=0;
		String pertama=null;
		try {
			String websiteRoot="D:\\";
			FileInputStream config=new FileInputStream("D:\\serverku\\config.txt");
			String configText=new String(config.readAllBytes());
			configText=configText.replaceAll("[\r\n]+", " ");
			String[] configArr=configText.split(" ");
			System.out.println(configArr[5]);
			
			InetAddress addr=InetAddress.getByName(configArr[1]);
//			InetAddress addr=(InetAddress) InetAddress.getLoopbackAddress();
			ServerSocket server=new ServerSocket(Integer.valueOf(configArr[3]),5,addr);
			System.out.println("1");
			String message;
			while(true) {
				Socket client=server.accept();
				System.out.println("2");
 
				BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				System.out.println("3");
				message=br.readLine();
				if(message.indexOf(".pdf")>=0||message.indexOf(".jpg")>=0) {
					String filetmp=message.split(" ")[1];
					filetmp=filetmp.substring(1);
					
					String url=br.readLine();
					if(url.indexOf("progjarku.com")>=0)websiteRoot=configArr[5];
					else if(url.indexOf("progjarkutercinta.com")>=0)websiteRoot=configArr[7];
					System.out.println("website lengkap:["+websiteRoot+filetmp+"]");
					
					FileInputStream newfis;
					newfis=new FileInputStream(websiteRoot+filetmp);
					String newfileContent=new String(newfis.readAllBytes());
//					System.out.println(newfileContent);
					
					File f = new File("D:\\serverku\\File\\"+filetmp);
		            BufferedWriter gambar = new BufferedWriter(new FileWriter(f));
		            
		            bw.write(newfileContent);
//		            System.out.println(newfileContent);
		            
		            bw.close();
		            bw.flush();
					continue;
				}
				else if(message.indexOf("html")<0) {
					System.out.println(message);
					System.out.println("kosong");continue;
				}
				
				
				String urn=message.split(" ")[1];
				
				String url=br.readLine();
				System.out.println("url:"+url);
				
			    if(url.indexOf(".com")>=0) {
					if(count>=1) {
						if(url.indexOf(pertama)<0)continue;
						
					}
						
				}
				
				if(url.indexOf("progjarku.com")>=0)websiteRoot=configArr[5];
				else if(url.indexOf("progjarkutercinta.com")>=0)websiteRoot=configArr[7];
			
				urn=urn.substring(1);
				System.out.println("dirnya"+websiteRoot+urn);
 
				FileInputStream fis=new FileInputStream(websiteRoot+urn);
				String fileContent=new String(fis.readAllBytes());
				System.out.println(fileContent);
 
 
				while(!message.isEmpty()) {
					System.out.println(message);
					message=br.readLine();
				}
				System.out.println("4");
//				System.out.println("From client: "+message);
				String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
 
				bw.write(inputan);
				bw.flush();	
				client.close();
				count++;
				if(count==1) {
					pertama=url.split(" ")[1];
//					System.out.println("url: pertama"+pertama);
				}
				
				
			}	
		}
		catch(IOException ex) {
//			Logger.getLogger(server.getName()).log(Level.SEVERE,null,ex);
		}
	}
	
	public static void soal_2() {
		
		try {
			String websiteRoot="D:\\serverku\\";
			ServerSocket server=new ServerSocket(80);
			System.out.println("1");
			while(true) {
				Socket client=server.accept();
				System.out.println("2");
 
				BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				System.out.println("3");
				String message=br.readLine();
				System.out.println(message);
				String urn=message.split(" ")[1];
				
				urn=urn.substring(1);				
				String url=br.readLine();
				System.out.println("url:"+url);
				
				if(urn.indexOf(".html")>=0) {
					System.out.println("web+urn:"+websiteRoot+urn);
					 
					FileInputStream fis=new FileInputStream(websiteRoot+urn);
					String fileContent=new String(fis.readAllBytes());
					System.out.println(fileContent);
	 
	 
					while(!message.isEmpty()) {
						System.out.println(message);
						message=br.readLine();
					}
					System.out.println("4");
					System.out.println("From client: "+message);
					String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
	 
					bw.write(inputan);
					bw.flush();	
					client.close();

				}
				
				else if(urn.indexOf(".html")<0) {
					System.out.println("slashslas: "+urn);
					String baru = null,tmp=null;
					try {
						  String dirLocation = "D:\\serverku\\"+urn;
						  System.out.println("dirlocaction: "+dirLocation);
						  if(!Files.exists(Paths.get(dirLocation)))continue;
					      List<File> myfile = Files.list(Paths.get(dirLocation))
					            .map(Path::toFile)
					            .collect(Collectors.toList());
					      
					      for(int i=0;i<myfile.size();i++) {
					    	  tmp=myfile.get(i).toString();
//					    	  System.out.println(tmp);
					    	  tmp=tmp.replaceAll("\\\\", " ");
					    	  tmp=tmp.split(" ")[2];
					    	  System.out.println(tmp);
					    	  baru="<a href=\"/" + tmp + "\">"+tmp+"</a><br>"+baru;
					      }
					    	  
					      if(myfile.size()<=0)continue;
					      
					     
					} catch (IOException e) {
					      // Error while reading the directory
					}
					
					
//					baru="<a href=\"/" + "index.html" + "\">cek</a><br>";
					String fileContent=new String(baru);
					String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
					System.out.println(inputan);
					bw.write(inputan);
					bw.flush();	
					client.close();
				}
				
			}	
		}
		catch(IOException ex) {
//			Logger.getLogger(server.getName()).log(Level.SEVERE,null,ex);
		}
		
	}
	
	
	
	public static void soal_3() {
		try {
			String websiteRoot="D:\\";
			ServerSocket server=new ServerSocket(80);
			System.out.println("1");
			while(true) {
				Socket client=server.accept();
				System.out.println("2");
 
				BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				System.out.println("3");
				String message=br.readLine();
				String urn=message.split(" ")[1];
				
				String url=br.readLine();
//				System.out.println("url:"+url);
				if(url.indexOf("progjarku.com")>=0)websiteRoot="D:\\serverku\\server1\\";
				else if(url.indexOf("progjarkutercinta.com")>=0)websiteRoot="D:\\serverku\\server2\\";
			
				urn=urn.substring(1);
				System.out.println(websiteRoot+urn);
 
				FileInputStream fis=new FileInputStream(websiteRoot+urn);
				String fileContent=new String(fis.readAllBytes());
				System.out.println(fileContent);
 
 
				while(!message.isEmpty()) {
					System.out.println(message);
					message=br.readLine();
				}
				System.out.println("4");
				System.out.println("From client: "+message);
				String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
 
				bw.write(inputan);
				bw.flush();	
				client.close();
			}	
		}
		catch(IOException ex) {
//			Logger.getLogger(server.getName()).log(Level.SEVERE,null,ex);
		}
	}
	
	public static void soal_4() {
		try {
			String websiteRoot="D:\\";
			FileInputStream config=new FileInputStream("D:\\serverku\\config.txt");
			String configText=new String(config.readAllBytes());
			configText=configText.replaceAll("[\r\n]+", " ");
			String[] configArr=configText.split(" ");
			System.out.println(configArr[5]);
			
			InetAddress addr=InetAddress.getByName(configArr[1]);
//			InetAddress addr=(InetAddress) InetAddress.getLoopbackAddress();
			ServerSocket server=new ServerSocket(Integer.valueOf(configArr[3]),5,addr);
			System.out.println("1");
			while(true) {
				Socket client=server.accept();
				System.out.println("2");
 
				BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				System.out.println("3");
				String message=br.readLine();
				String urn=message.split(" ")[1];
				
				String url=br.readLine();
//				System.out.println("url:"+url);
				if(url.indexOf("progjarku.com")>=0)websiteRoot=configArr[5];
				else if(url.indexOf("progjarkutercinta.com")>=0)websiteRoot=configArr[7];
			
				urn=urn.substring(1);
				System.out.println(websiteRoot+urn);
 
				FileInputStream fis=new FileInputStream(websiteRoot+urn);
				String fileContent=new String(fis.readAllBytes());
				System.out.println(fileContent);
 
 
				while(!message.isEmpty()) {
					System.out.println(message);
					message=br.readLine();
				}
				System.out.println("4");
				System.out.println("From client: "+message);
				String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
 
				bw.write(inputan);
				bw.flush();	
				client.close();
			}	
		}
		catch(IOException ex) {
//			Logger.getLogger(server.getName()).log(Level.SEVERE,null,ex);
		}
	}
	
	public static void soal_5() {
		int count=0;
		String pertama=null;
		try {
			String websiteRoot="D:\\";
			FileInputStream config=new FileInputStream("D:\\serverku\\config.txt");
			String configText=new String(config.readAllBytes());
			configText=configText.replaceAll("[\r\n]+", " ");
			String[] configArr=configText.split(" ");
			System.out.println(configArr[5]);
			
			InetAddress addr=InetAddress.getByName(configArr[1]);
//			InetAddress addr=(InetAddress) InetAddress.getLoopbackAddress();
			ServerSocket server=new ServerSocket(Integer.valueOf(configArr[3]),5,addr);
			System.out.println("1");
			String message;
			while(true) {
				Socket client=server.accept();
				System.out.println("2");
 
				BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				System.out.println("3");
				message=br.readLine();
				if(message.indexOf("html")<0) {
					System.out.println(message);
					System.out.println("kosong");continue;
				}
				
				
				String urn=message.split(" ")[1];
				
				String url=br.readLine();
				System.out.println("url:"+url);
				
			    if(url.indexOf(".com")>=0) {
					if(count>=1) {
						if(url.indexOf(pertama)<0)continue;
						
					}
						
				}
				
				if(url.indexOf("progjarku.com")>=0)websiteRoot=configArr[5];
				else if(url.indexOf("progjarkutercinta.com")>=0)websiteRoot=configArr[7];
			
				urn=urn.substring(1);
				System.out.println("dirnya"+websiteRoot+urn);
 
				FileInputStream fis=new FileInputStream(websiteRoot+urn);
				String fileContent=new String(fis.readAllBytes());
				System.out.println(fileContent);
 
 
				while(!message.isEmpty()) {
					System.out.println(message);
					message=br.readLine();
				}
				System.out.println("4");
//				System.out.println("From client: "+message);
				String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
 
				bw.write(inputan);
				bw.flush();	
				client.close();
				count++;
				if(count==1) {
					pertama=url.split(" ")[1];
//					System.out.println("url: pertama"+pertama);
				}
				
				
			}	
		}
		catch(IOException ex) {
//			Logger.getLogger(server.getName()).log(Level.SEVERE,null,ex);
		}
	}
	
}
