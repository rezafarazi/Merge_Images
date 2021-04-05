package merge_sevral_image;

import com.jfoenix.controls.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.swing.JFileChooser;

public class MainPageController implements Initializable
{
    
    //Get Globla Variables Start
    @FXML
    private JFXTextField width_textfeild;

    @FXML
    private JFXTextField height_textfeild;

    @FXML
    private JFXTextField save_address_textfeild;

    @FXML
    private JFXTextField new_image_address_textfeild;

    @FXML
    private VBox new_address_list;
    
    ArrayList<String> select_files=new ArrayList<String>();
    //Get Globla Variables End
    
    
    //Get Chosee New File From Pc Storage Start
    @FXML
    void chosee_new_file(ActionEvent event) 
    {
        if(new_image_address_textfeild.getText().equals(""))
        {
            javax.swing.JFileChooser file_selector=new JFileChooser();
            if(file_selector.showOpenDialog(null)==javax.swing.JFileChooser.APPROVE_OPTION)
            {
                String Save_Loc=file_selector.getSelectedFile().getPath();
                new_image_address_textfeild.setText(Save_Loc);
            }
        }
        
        select_files.add(new_image_address_textfeild.getText());
        Text text=new Text(new_image_address_textfeild.getText());
        new_address_list.getChildren().add(text);
        new_image_address_textfeild.clear();
    }
    //Get Chosee New File From Pc Storage End

    
    //Chosee Save Image File Location Start
    @FXML
    void chosee_save_file(ActionEvent event) 
    {
        javax.swing.JFileChooser file_selector=new JFileChooser();
        if(file_selector.showSaveDialog(null)==javax.swing.JFileChooser.APPROVE_OPTION)
        {
            String Save_Loc=file_selector.getSelectedFile().getAbsolutePath();
            save_address_textfeild.setText(Save_Loc);
        }
    }
    //Chosee Save Image File Location End

    
    //On Click Render Event Start
    @FXML
    void on_click_render(ActionEvent event) 
    {
        GET_COPY_ALL_IMAGES_TO_MY_PATH();
        GET_GENERATE_PYTHON_CODE();
    }
    //On Click Render Event End
    
    //Get Copy All Images To My Path Start
    public void GET_COPY_ALL_IMAGES_TO_MY_PATH()
    {
        int errs=0;
        for(int i=0;i<100;i++)
        {
            try
            {
                File final_file=new File(i+".png");
                Files.delete(final_file.toPath());
            }
            catch(Exception Err)
            {
                errs++;
                if(errs>=10)
                {
                    break;
                }
            }
        }
        
        for(int i=0;i<select_files.size();i++)
        {
            try
            {
                File file=new File(select_files.get(i));
                File final_file=new File(i+".png");
                Files.copy(file.toPath(),final_file.toPath());
            }
            catch(Exception Err)
            {
                System.out.println(Err.getMessage());
            }
        }
    }
    //Get Copy All Images To My Path End
    
    
    //Get Images Start
    public String GET_READ_FILES()
    {
        String Finall="[";
        try
        {
            for(int i=0;i<100;i++)
            {
                File f=new File(i+".png");
                if(f.exists())
                {
                    if(i!=0)
                        Finall+=",";
                        
                    Finall+="\""+i+".png\"";
                }
                else
                {
                    break;
                }
            }
            Finall+="]";
            return Finall;
        }
        catch(Exception Err)
        {
            
        }
        return "[]";
    }
    //Get Images End
    
    
    //Get Generate Python Code Start
    public void GET_GENERATE_PYTHON_CODE()
    {        
        String Sv=save_address_textfeild.getText().toString();
        String Images=GET_READ_FILES();
        String Python_Code="#Libraries Start\n\r" +
                            "from PIL import Image\n\r" +
                            "#Libraries End\n\r" +
                            "\n\r" +
                            "#Global Varaibles Start\n\r" +
                            "Images_Addrs="+Images+"  \n\r" +
                            "Images_Img=[]\n\r" +
                            "#Global Varaibles End\n\r" +
                            "\n\r" +
                            "#Get Mirage Start\n\r" +
                            "def Miarge_Images_Addrs_Array():\n\r" +
                            "    \n\r" +
                            "    #Get Read Image From Address Start\n\r" +
                            "    for i in Images_Addrs:\n\r" +
                            "        Images_Img.append(Image.open(i))\n\r" +
                            "    #Get Read Image From Address End    \n\r" +
                            "\n\r" +
                            "    Width= "+width_textfeild.getText().toString()+" \n\r" +
                            "    Heigth="+height_textfeild.getText().toString()+"\n\r" +
                            "\n\r" +
                            "    Mrg_Image=Image.new('RGB',(Width,Heigth))\n\r" +
                            "\n\r" +
                            "    for Imgs in Images_Img :\n\r" +
                            "        Mrg_Image.paste(Imgs)\n\r" +
                            "\n\r" +
                            "    Mrg_Image.save('"+Sv+"')\n\r" +
                            "\n\r" +
                            "#Get Mirage End\n\r" +
                            "\n\r" +
                            "#Main Function Start\n\r" +
                            "def main():\n\r" +
                            "    Miarge_Images_Addrs_Array()\n\r" +
                            "#Main Function End\n\r" +
                            "\n\r" +
                            "#Main Runner Start\n\r" +
                            "if __name__=='__main__':\n\r" +
                            "    main()\n\r" +
                            "#Main Runner End";
        try
        {
            File file=new File("merge.py");            
            FileWriter w=new FileWriter(file);
            w.write(Python_Code);
            w.close();            
        }
        catch(Exception Err)
        {
            
        }
        
        
        
        try
        {
            Runtime rt=Runtime.getRuntime();
            rt.exec("python merge.py");
        }
        catch(Exception e)
        {
            
        }
        
        
        
        System.exit(0);
    }
    //Get Generate Python Code End
    
    
    
    //Get Initializing Function Start
    @Override
    public void initialize(URL url,ResourceBundle rb)
    {
        
    }    
    //Get Initializing Function End
    
}
