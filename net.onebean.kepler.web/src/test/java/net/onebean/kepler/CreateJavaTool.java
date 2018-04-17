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
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.onebean.util.StringUtils;



public class CreateJavaTool {
	private JFrame frame;
	private JTextField tabName_text;
	private JTextField modelName_text;
	private JTextField modelPath_text;
	private JTextField daoPath_text;
	private JTextField servicePath_text;
	private String workspacePath;
	private String modelPath ;
	private String mapperPath ;
	private String daoPath ;
	private String servicePath ;
	private String serviceImplPath ;
	
	private static final int WIN_WIDTH = 525;
	private static final int WIN_HEIGHT = 300;
	private static final String projectName = "net.onebean.kepler";

	public CreateJavaTool(){
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
		
		JLabel lab = new JLabel("表名");
		lab.setBounds(20, 20, 100, 30);
		frame.add(lab);
		tabName_text = new  JTextField(30);
		tabName_text.setBounds(100,20,375,30);
		frame.add(tabName_text);
		
		lab = new JLabel("Model名称");
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
		workspacePath = proPath+"\\"+projectName+"\\";
		modelPath = workspacePath +"net.onebean.kepler.dao\\src\\main\\java\\net\\onebean\\kepler\\model\\";
		mapperPath = modelPath + "mappings\\";
		daoPath = workspacePath + "net.onebean.kepler.dao\\src\\main\\java\\net\\onebean\\kepler\\dao\\";
		servicePath = workspacePath + "net.onebean.kepler.service\\src\\main\\java\\net\\onebean\\kepler\\service\\";
		serviceImplPath = servicePath +"impl\\";
		daoPath_text.setText(daoPath);
		modelPath_text.setText(modelPath);
		servicePath_text.setText(servicePath);
	}
	
	protected void execute(){
		if(StringUtils.isEmpty(tabName_text.getText())){
			JOptionPane.showMessageDialog(frame, "表名不能为空");
			return;
		}
		if(StringUtils.isEmpty(modelName())){
			JOptionPane.showMessageDialog(frame, "model名称不能为空");
			return;
		}
		SimpleJDBC jdbc = new SimpleJDBC();
		List<Map<String,Object>> columns = jdbc.getColumns(tabName_text.getText());
		executeModel(columns);
		executeMapper();
		executeDao();
		executeService();
		executeServiceImpl();
		JOptionPane.showMessageDialog(frame, "创建完成，刷新目录查看");
		System.exit(0);
	}
	
	protected void executeModel(List<Map<String,Object>> columns){
		try {
			StringBuffer sb = new StringBuffer("package net.onebean.kepler.model;\nimport net.onebean.core.extend.FiledName;\nimport net.onebean.core.extend.TableName;"
					+ "\nimport net.onebean.core.model.BaseModel;\nimport java.io.Serializable;\nimport java.sql.Timestamp;");
			sb.append("\n\n@TableName(\""+tabName_text.getText()+"\")"); 
			sb.append("\npublic class "+modelName()+" extends BaseModel{");
			for(Map<String,Object> column : columns){
				String field = column.get("name").toString().toLowerCase();
				if(field.equals("id") || field.equals("createdate")) continue;
				String type = column.get("type").toString().toLowerCase();
				String classes = "String";
				if(type.indexOf("int") != -1){
					classes = "Integer";
				}
				if(type.indexOf("bigint") != -1){
					classes = "Long";
				}
				if(type.indexOf("numeric") != -1 || type.indexOf("double") != -1 || type.indexOf("decimal") != -1){
					classes = "Double";
				}
				if(type.indexOf("float") != -1){
					classes = "Float";
				}
				if(type.indexOf("time") != -1 || type.indexOf("date") != -1){
					classes = "Timestamp";
				}
				sb.append("\n\n\tprivate "+classes +" "+ field+";");
			
				String first = field.substring(0, 1);
				String content = field.substring(1, field.length());
				
				//getter
				sb.append("\n\t@FiledName(\""+field+"\")");
				sb.append("\n\tpublic "+ classes+ " get"+first.toUpperCase()+content+"(){");
				sb.append("\n\t\treturn this."+field+";");
				sb.append("\n\t}");
				
				//setter
				sb.append("\n\tpublic void set"+first.toUpperCase()+content+"("+classes+" "+field+"){");
				sb.append("\n\t\t this."+field+" = "+ field+";");
				sb.append("\n\t}");
			}
			
			sb.append("\n}");
			File f = new File(modelPath + "" + modelName()+ ".java");
			FileWriter fileWriter = new FileWriter(f);  
			fileWriter.write(sb.toString());  
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	
	protected void executeMapper(){
		try {
			String str ="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
					+ "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >"
					+ "\n<mapper namespace=\"net.onebean.kepler.dao."+modelName()+"Dao\" ></mapper>";
			File f = new File(mapperPath + "" + modelName()+ "Mapper.xml");
			FileWriter fileWriter;
			fileWriter = new FileWriter(f);
			fileWriter.write(str);  
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	protected void executeDao(){
		try {
			StringBuffer sb = new StringBuffer("package net.onebean.kepler.dao;\nimport net.onebean.core.BaseDao;\nimport net.onebean.kepler.model."+modelName()+";");
			sb.append("\n\npublic interface "+modelName()+"Dao extends BaseDao<"+modelName()+"> {");
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
			StringBuffer sb = new StringBuffer("package net.onebean.kepler.service;\nimport net.onebean.core.IBaseBiz;\nimport net.onebean.kepler.model."+modelName()+";");
			sb.append("\n\npublic interface "+modelName()+"Service extends IBaseBiz<"+modelName()+"> {");
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
			StringBuffer sb = new StringBuffer("package net.onebean.kepler.service.impl;\nimport org.springframework.stereotype.Service;"
					+ "\nimport net.onebean.core.BaseBiz;"
					+ "\nimport net.onebean.kepler.model."+modelName()
					+";\nimport net.onebean.kepler.service."+modelName()+"Service;"
					+ "\nimport net.onebean.kepler.dao."+modelName()+"Dao;");
			sb.append("\n\n@Service");
			sb.append("\npublic class "+modelName()+"ServiceImpl extends BaseBiz<"+modelName()+", "+modelName()+"Dao> implements "+modelName()+"Service{");
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
		new CreateJavaTool();
	}
}
