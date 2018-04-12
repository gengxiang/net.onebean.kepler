package net.onebean.kepler;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.onebean.util.StringUtils;



public class CreateMongoJavaTool {
	 
	private JFrame frame;
	private JTextField modelName_text;
	private JTextField modelPath_text;
	private JTextField daoPath_text;
	private JTextField servicePath_text;
	private String workspacePath;
	private String modelPath ;
	private String daoPath ;
	private String servicePath ;
	private String serviceImplPath ;
	
	private static final int WIN_WIDTH = 525;
	private static final int WIN_HEIGHT = 300;
	public CreateMongoJavaTool(){
		init();
	} 

	protected void init(){
		frame = new JFrame();
		frame.setSize(WIN_WIDTH, WIN_HEIGHT);
		frame.setTitle("自动生成model,dao、service代码");
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		Dimension screenSize = kit.getScreenSize(); 
		int screenWidth = screenSize.width; 
		int screenHeight = screenSize.height; 
		frame.setLocation(screenWidth / 2 - WIN_WIDTH / 2, screenHeight / 2 - WIN_HEIGHT / 2);
		frame.setLayout(null);
		
		
		JLabel lab = new JLabel("Model名称");
		lab.setBounds(20, 50, 100, 30);
		frame.add(lab);
		modelName_text = new  JTextField(30);
		modelName_text.setBounds(100,50,375,30);
		frame.add(modelName_text);
		
		lab = new JLabel("Model路径:");
		lab.setBounds(20, 80, 100, 30);
		frame.add(lab);
		modelPath_text = new  JTextField(30);
		modelPath_text.setBounds(100,80,375,30);
		frame.add(modelPath_text);
		
		lab = new JLabel("Dao路径");
		lab.setBounds(20, 110, 100, 30);
		frame.add(lab);
		daoPath_text = new  JTextField(30);
		daoPath_text.setBounds(100,110,375,30);
		frame.add(daoPath_text);
		
		lab = new JLabel("Service路径:");
		lab.setBounds(20, 140, 100, 30);
		frame.add(lab);
		servicePath_text = new JTextField(30);
		servicePath_text.setBounds(100,140,375,30);
		frame.add(servicePath_text);
		
		JButton btn = new JButton("确定");
		btn.setBounds(WIN_WIDTH/2 - 50 / 2, 180, 80, 30);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				execute();
			}
		});
		frame.add(btn);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter()  {
			     public void windowClosing(WindowEvent e) {
				        System.exit(0);
				     }
			    });
		initPath();
	}
	
	protected void initPath(){
		String proPath = System.getProperty("user.dir");
		proPath = proPath.replaceAll("\\\\", "\\\\\\\\");
		workspacePath = new File(proPath).getParent()+"\\";
		modelPath = workspacePath +"net.onebean.kepler.dao\\src\\main\\java\\com\\onebean\\kepler\\model\\mongo\\";
		daoPath = workspacePath + "net.onebean.kepler.dao\\src\\main\\java\\com\\onebean\\kepler\\dao\\mongo\\";
		servicePath = workspacePath + "net.onebean.kepler.service\\src\\main\\java\\com\\onebean\\kepler\\service\\mongo\\";
		serviceImplPath = workspacePath + "net.onebean.kepler.service\\src\\main\\java\\com\\onebean\\kepler\\service\\impl\\mongo\\";
		daoPath_text.setText(daoPath);
		modelPath_text.setText(modelPath);
		servicePath_text.setText(servicePath);
	}
	
	protected void execute(){
		if(StringUtils.isEmpty(modelName())){
			JOptionPane.showMessageDialog(frame, "model名称不能为空");
			return;
		}
		executeModel();
		executeDao();
		executeService();
		executeServiceImpl();
		JOptionPane.showMessageDialog(frame, "创建完成，刷新目录查看");
		System.exit(0);
	}
	
	protected void executeModel(){
		try {
			StringBuffer sb = new StringBuffer("package net.onebean.kepler.model.mongo;\n\nimport net.onebean.core.model.BaseModelMongo;");
			sb.append("\n\n@SuppressWarnings(\"serial\")");
			sb.append("\npublic class "+modelName()+" extends BaseModelMongo{");
			
			sb.append("\n}");
			File f = new File(modelPath + "" + modelName()+ ".java");
			FileWriter fileWriter = new FileWriter(f);  
			fileWriter.write(sb.toString());  
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	
	
	protected void executeDao(){
		try {
			StringBuffer sb = new StringBuffer("package net.onebean.kepler.dao.mongo;\n\nimport org.springframework.stereotype.Component;"
			+"\nimport net.onebean.kepler.model.mongo."+modelName()+";"
			+"\nimport BaseMongoDao;");
			sb.append("\n\n@Component");
			sb.append("\npublic class "+modelName()+"Dao extends BaseMongoDao<"+modelName()+"> {");
			sb.append("\n}");
			File f = new File(daoPath + modelName()+ "Dao.java");
			FileWriter fileWriter;
			fileWriter = new FileWriter(f);
			fileWriter.write(sb.toString());  
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	protected void executeService(){
		try {
			StringBuffer sb = new StringBuffer(" package net.onebean.kepler.service.mongo;\n\nimport IBaseMongoBiz;"
			+"\nimport net.onebean.kepler.model.mongo."+modelName()+";");
			sb.append("\n\npublic interface "+modelName()+"Service extends IBaseMongoBiz<"+modelName()+"> {");
			sb.append("\n}");
			File f = new File(servicePath + "" + modelName()+ "Service.java");
			FileWriter fileWriter;
			fileWriter = new FileWriter(f);
			fileWriter.write(sb.toString());  
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	protected void executeServiceImpl(){
		try {
			StringBuffer sb = new StringBuffer("package net.onebean.kepler.service.impl.mongo;\n\nimport org.springframework.stereotype.Service;"
					+ "\nimport BaseMongoBiz;"
					+ "\nimport net.onebean.kepler.model.mongo."+modelName()
					+";\nimport net.onebean.kepler.service.mongo."+modelName()+"Service;"
					+ "\nimport net.onebean.kepler.dao.mongo."+modelName()+"Dao;");
			sb.append("\n\n@Service");
			sb.append("\npublic class "+modelName()+"ServiceImpl extends BaseMongoBiz<"+modelName()+", "+modelName()+"Dao> implements "+modelName()+"Service{");
			sb.append("\n}");
			File f = new File(serviceImplPath + "" + modelName()+ "ServiceImpl.java");
			FileWriter fileWriter;
			fileWriter = new FileWriter(f);
			fileWriter.write(sb.toString());  
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	private String modelName(){
		return modelName_text.getText();
	}
	
	
	public static void main(String[] ags){
		new CreateMongoJavaTool();
	}
}
