package us.bney.sinks.IO;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Scanners
{
  public static JSONArray scanDirectory(File toScan)
  {
    if(toScan.isDirectory())
    {
      JSONArray toReturn = new JSONArray();
      ArrayDeque<File> toScanDirectories = new ArrayDeque<File>();
      toScanDirectories.add(toScan);
      
      while(!toScanDirectories.isEmpty())
      {
        File workingDirectory = toScanDirectories.poll();
        for(File a : workingDirectory.listFiles())
        {
          if(a.isDirectory())
          {
            toScanDirectories.add(a);
          }
          else
          {
            Path top = Paths.get(toScan.toURI());
            Path bottom = Paths.get(a.toURI());
            Path relative = top.relativize(bottom);
            
            MessageDigest md = null;
            try
            {
              md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e1)
            {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
            
            byte[] data = null;
            try
            {
              data = Files.readAllBytes(a.toPath());
            } catch (IOException e)
            {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            
            JSONObject fileData = new JSONObject();
            fileData.put("path", relative);
            fileData.put("hash", new BigInteger(1, md.digest(data)).toString(16));
            try
            {
              fileData.put("size", Files.size(a.toPath()));
            } catch (JSONException e)
            {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } catch (IOException e)
            {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            
            toReturn.put(fileData);
          }
        }
      }
      return toReturn;
    }
    else
    {
      System.err.println("ERROR: Argument toScan not a directory. Returning null");
      return null;
    }
  }
  
  public static JSONArray scanDirectory(File toScan, Set<File> blackList)
  {
    if(toScan.isDirectory())
    {
      JSONArray toReturn = new JSONArray();
      ArrayDeque<File> toScanDirectories = new ArrayDeque<File>();
      toScanDirectories.add(toScan);
      
      while(!toScanDirectories.isEmpty())
      {
        File workingDirectory = toScanDirectories.poll();
        for(File a : workingDirectory.listFiles())
        {
          if(a.isDirectory())
          {
            toScanDirectories.add(a);
          }
          else if(!blackList.contains(a))
          {
            Path top = Paths.get(toScan.toURI());
            Path bottom = Paths.get(a.toURI());
            Path relative = top.relativize(bottom);
            
            MessageDigest md = null;
            try
            {
              md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e1)
            {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
            
            byte[] data = null;
            try
            {
              data = Files.readAllBytes(a.toPath());
            } catch (IOException e)
            {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            
            JSONObject fileData = new JSONObject();
            fileData.put("path", relative);
            fileData.put("hash", new BigInteger(1, md.digest(data)).toString(16));
            try
            {
              fileData.put("size", Files.size(a.toPath()));
            } catch (JSONException e)
            {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } catch (IOException e)
            {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            
            toReturn.put(fileData);
          }
        }
      }
      return toReturn;
    }
    else
    {
      System.err.println("ERROR: Argument toScan not a directory. Returning null");
      return null;
    }
  }
}
