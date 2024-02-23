package juke;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;



        import javax.sound.sampled.AudioInputStream;
        import javax.sound.sampled.AudioSystem;
        import javax.sound.sampled.Clip;
        import javax.sound.sampled.LineUnavailableException;
        import javax.sound.sampled.UnsupportedAudioFileException;

public class Playlist
{


    Long currentFrame;
    Clip clip;
    // current status of clip
    String status;
    AudioInputStream audioInputStream;
    String filePath;

    public static void main(String[] args)
    {

        System.out.println("-------------------------------Welcome to Music World--------------------------------------");
        System.out.println(" -----------------------------Sounds That Make you Move------------------------------------");
        try
        {

            Scanner sc = new Scanner(System.in);//input using to get


            while (true) //to get choices in loop
            {
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("1. create playlist");
                System.out.println("2. add a song or album");
                System.out.println("3. view playlist");
                System.out.println("4. search categories");
                System.out.println("5. view all songs");
                System.out.println("6. exit");
                System.out.println("-----------------------------------------------------------------------------");
                System.out.println("Enter your choice");
                int c2 = sc.nextInt();
                Playlist p=new Playlist();
                p.gotoChoice(c2);
                if (c2 == 6)
                    break;
            }
            sc.close();
        }

        catch (Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }
    }

    // Work as the user enters his choice

    private void gotoChoice(int c3) throws Exception, LineUnavailableException, UnsupportedAudioFileException
    {

        Scanner sc = new Scanner(System.in);
        Class.forName("com.mysql.jdbc.Driver");

        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/playlist","root","root");
        String pname,sname,spath,aname,gen,s="";
        int ch;
        switch(c3)
        {
            case 1:
                System.out.println("Enter playlist name");
                pname=sc.next();
                PreparedStatement ps=con.prepareStatement("insert into plist values(?)");
                ps.setString(1,pname);
                ps.executeUpdate(); //insert in query prepared statement to write query
                break;
            case 2:
                System.out.println("Enter playlist name to add songs");
                pname=sc.next();
                System.out.println("Enter song name");
                sname=sc.next();
                System.out.println("Enter song path");
                spath=sc.next();
                System.out.println("Enter artist name");
                aname=sc.next();
                System.out.println("Enter genre");
                gen=sc.next();
                PreparedStatement ps1=con.prepareStatement("insert into song values(?,?,?,?,?)");
                ps1.setString(1,pname);
                ps1.setString(2,sname);
                ps1.setString(3,spath);
                ps1.setString(4,aname);
                ps1.setString(5,gen);
                ps1.executeUpdate();//to save the record which is given
                break;
            case 3:
                System.out.println("Enter playlist name to view");
                pname=sc.next();
                PreparedStatement ps2=con.prepareStatement("select * from song where pname=?");
                ps2.setString(1,pname);
                ResultSet rs=ps2.executeQuery();
                System.out.println("Playlist name,song name,path,artist name,genre");
                while(rs.next()) //resultset
                {
                    System.out.println(rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+","+rs.getString(4)+","+rs.getString(5));
                }
                System.out.println("-----------------------------------------------------------------------------");
                break;
            case 4:

                System.out.println("Catalog List:\n1.search songs by Artist Name\n2.search songs by Genre\n3.search songs by Song Name\nEnter your choice");

                ch=sc.nextInt();
                if(ch==1)
                {
                    System.out.println("Enter artist name");
                    aname=sc.next();
                    PreparedStatement ps3=con.prepareStatement("select * from song where aname=?");
                    ps3.setString(1,aname);
                    ResultSet rs1=ps3.executeQuery();
                    System.out.println("Playlist name,song name,path,artist name,genre");
                    while(rs1.next())
                    {
                        System.out.println(rs1.getString(1)+","+rs1.getString(2)+","+rs1.getString(3)+","+rs1.getString(4)+","+rs1.getString(5));
                        System.out.println("-----------------------------------------------------------------------------");
                    }
                }
                else if(ch==2)
                {
                    System.out.println("Enter genre");
                    gen=sc.next();
                    PreparedStatement ps3=con.prepareStatement("select * from song where gen=?");
                    ps3.setString(1,gen);
                    ResultSet rs1=ps3.executeQuery();
                    System.out.println("Playlist name,song name,path,artist name,genre");
                    while(rs1.next())
                    {
                        System.out.println(rs1.getString(1)+","+rs1.getString(2)+","+rs1.getString(3)+","+rs1.getString(4)+","+rs1.getString(5));
                        System.out.println("-----------------------------------------------------------------------------");
                    }
                }
                else if(ch==3)
                {
                    System.out.println("Enter song name");
                    sname=sc.next();
                    PreparedStatement ps3=con.prepareStatement("select * from song where sname=?");
                    ps3.setString(1,sname);
                    ResultSet rs1=ps3.executeQuery();
                    System.out.println("Playlist name,song name,path,artist name,genre");
                    System.out.println("-----------------------------------------------------------------------------");
                    while(rs1.next())
                    {
                        System.out.println(rs1.getString(1)+","+rs1.getString(2)+","+rs1.getString(3)+","+rs1.getString(4)+","+rs1.getString(5));
                    }
                    //filePath = sname+".wav";

                    filePath="C:\\Java Workspace\\MYproject\\src\\main\\resources\\"+sname+".wav";
                    System.out.println(filePath);
                    audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    play();

                    while (true)
                    {
                        System.out.println("1. pause");
                        System.out.println("2. resume");
                        System.out.println("3. restart");
                        System.out.println("4. stop");
                        System.out.println("5. Jump to specific time");
                        System.out.println("-----------------------------------------------------------------------------");
                        int c1 = sc.nextInt();
                        gotoChoice1(c1);
                        if (c1 == 4)
                            break;
                    }



                }
                break;
            case 5:


                    PreparedStatement ps3=con.prepareStatement("select * from song");
                    ResultSet rs1=ps3.executeQuery();
                    System.out.println("song names");
                    System.out.println("-----------------------------------------------------------------------------");
                    while(rs1.next())
                    {
                        System.out.println(rs1.getString(2)+",");
                    }
                    break;

        }


    }


    private void gotoChoice1(int c) throws IOException, LineUnavailableException, UnsupportedAudioFileException
    {
        switch (c)
        {
            case 1:
                pause();
                break;
            case 2:
                resumeAudio();
                break;
            case 3:
                restart();
                break;
            case 4:
                stop();
                break;
            case 5:
                System.out.println("Enter time (" + 0 +
                        ", " + clip.getMicrosecondLength() + ")");
                Scanner sc = new Scanner(System.in);
                long c1 = sc.nextLong();
                jump(c1);
                break;

        }

    }

    // Method to play the audio
    public void play()
    {
        //start the clip
        clip.start();

        status = "play";
    }

    // Method to pause the audio
    public void pause()
    {
        if (status.equals("paused"))
        {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame =
                this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    // Method to resume the audio
    public void resumeAudio() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException     //exception handling thjey are class
    {
        if (status.equals("play"))
        {
            System.out.println("Audio is already "+
                    "being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

    // Method to restart the audio
    public void restart() throws IOException, LineUnavailableException,UnsupportedAudioFileException
    {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    // Method to stop the audio
    public void stop() throws UnsupportedAudioFileException,IOException, LineUnavailableException
    {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }

    // Method to jump over a specific part
    public void jump(long c) throws UnsupportedAudioFileException, IOException,LineUnavailableException
    {
        if (c > 0 && c < clip.getMicrosecondLength())
        {
            clip.stop();
            clip.close();
            resetAudioStream();
            currentFrame = c;
            clip.setMicrosecondPosition(c);
            this.play();
        }
    }

    // Method to reset audio stream
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException,LineUnavailableException
    {
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}


