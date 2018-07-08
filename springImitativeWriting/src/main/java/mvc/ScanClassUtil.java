package mvc;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.net.JarURLConnection;

/**
 * 扫描某个包下面的类的工具类
 * @author liang
 *
 */
public class ScanClassUtil {

	/**
	 * 从包package中获取所有的class
	 * packag 包路径
	 * @return
	 */
	public static Set<Class<?>> getClasses(String packag) {
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		boolean recursive = true;//是否循环迭代
		String packageName = packag;
		String packageDirName = packag.replace('.', '/');
		//定义枚举类的集合，并进行循环处理这个目录下的
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);//获取当前的线程的类加载器获取文件
			//循环迭代
			while(dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();//获取协议名称
				if("file".equals(protocol)) {
					System.err.println("file类型的扫描");
					//获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(),"UTF-8");
					//以文件方式扫描整个包下的文件，并添加到集合
					findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
				}else if("jar".equals(protocol)){
					System.err.println("jar类型的扫描");
					JarFile jar;
					try {
						jar = ((JarURLConnection)url.openConnection()).getJarFile();//打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）
						//从此jar包获取一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						while(entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							if(name.charAt(0) == '/') {// 以/开头
								//获取后面的字符串
								name = name.substring(1);
							}
							if(name.startsWith(packageDirName)) {//前部分与定义包名一致
								int idx = name.lastIndexOf('/');
								//如果以/结尾，是一个包
								if(idx !=-1) {
									//获取包名，把/替换为.
									packageDirName = name.substring(0,idx).replace('/', '.');
								}
								//如果可以迭代下去，并且是一个包
								if(idx !=-1 || recursive) {
									//如果是一个.class文件，并且不是目录
									if(name.endsWith(".class") && !entry.isDirectory()) {
										//去掉.class获取真正的类名
										String className = name.substring(packageName.length()+1,name.length()-6);
										try {
											classes.add(Class.forName(packageName+'.'+className));
										} catch (ClassNotFoundException e) {
											// TODO: handle exception
											e.printStackTrace();
										}
									}
								}
							}
						}
					
					} catch (IOException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
				}
			}
		} catch (IOException  e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return classes;
		
	}
	
	/**
	 * 以文件的形式来获取包下的所有Class
	 * @param packageName 包名字xxx.xxx.xxx.xx
	 * @param packagePath 包路径xxx/xxx/xxx/xx
	 * @param recursive  是否循环迭代
	 * @param classes	第一个class类的集合
	 */
	public static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Set<Class<?>> classes) {
		File dir = new File(packagePath);
		//如果不存在也不是目录
		if(!dir.exists() || !dir.isDirectory()) {
			return;
		}
		
		//如果存在则获取包下所有文件，包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			 // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return (recursive && pathname.isDirectory()) || (pathname.getName().endsWith(".class"));
			}
			
		});
		
		//循环所有文件
		for(File file:dirfiles) {
			//如果是目录，则继续扫描
			if(file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName+"."+file.getName(),file.getAbsolutePath(),recursive,classes);
			}else {
				//如果是java类文件，去掉后面的.class 只留下类名
				String classname = file.getName().substring(0, file.getName().length()-6);
				try {
					classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName+"."+classname));//类装载
				}catch (ClassNotFoundException e) {
                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
			}
		}
	}
}
