package cn.edu.ncut.istc.nn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.joone.engine.*;
import org.joone.engine.learning.*;
import org.joone.io.*;
import org.joone.net.*;

public class SerialNeuralNet implements NeuralNetListener, Serializable {

	private static final long serialVersionUID = 1L;

	private static final int SIZE = 8;// 这个是设置神经网络的输入层的训练数据大小size，使用的是数组长度

	// TRAIN input
	private static final int INSTARTCOL = 1;
	private static final int INEDNCOL = 4;
	// TRAIN disire
	private static final int DISSTARTCOL = 1;
	private static final int DISENDCOL = 1;

	// TEST
	private static final int TESTSTARTCOL = 1;
	private static final int TESTENDCOL = 4;
	private static final int TESTNUM = 4;

	// init
	private static final int TRAINROW = 8;

	private static final String SAVENNET = "C:\\nn\\nnet.txt";
	private static final String SAVEFILE = "C:\\nn\\file.txt";

	private File inputFile = new File("C:\\nn\\input.txt");
	private File inputFile2 = new File("C:\\nn\\input2.txt");
	private File testInputFile = new File("C:\\nn\\test.txt");

	private NeuralNet nnet = null;
	private FileInputSynapse fileInputSynapse, fileDisireOutputSynapse;

	LinearLayer input;
	SigmoidLayer hidden, output;
	boolean singleThreadMode = true;

	/**
	 * @param args
	 *            先初始化 训练 测试
	 */
	public static void main(String args[]) {
		SerialNeuralNet xor = new SerialNeuralNet();

		xor.initNeuralNet();
		xor.train();

		xor.interrogate();
	}

	/**
	 * Method declaration
	 */
	protected void initNeuralNet() {
		/**
		 * input=new LinearLayer()是新建一个输入层，因为神经网络的输入层并没有训练参数，所以使用的是线性层；
		 */
		/**
		 * hidden = new SigmoidLayer();这里是新建一个隐含层，使用sigmoid函数作为激励函数，
		 * 当然你也可以选择其他的激励函数，如softmax激励函数
		 */
		// First create the three layers
		input = new LinearLayer();
		hidden = new SigmoidLayer();
		output = new SigmoidLayer();

		/**
		 * 建立输入层、隐含层、输出层的神经元个数， 这里表示输入层为2个神经元，隐含层是3个神经元，输出层是1个神经元
		 */
		// set the dimensions of the layers
		input.setRows(TRAINROW); // 此参数系统会自动设置 调用Layer的
		// fireFwdGet中的adjustSizeToFwdPattern
		hidden.setRows(3);
		output.setRows(1);

		// 给每个输出层取一个名字
		input.setLayerName(" L.input ");
		hidden.setLayerName(" L.hidden ");
		output.setLayerName(" L.output ");

		/**
		 * 主要作用是将三个层连接起来，synapse_IH用来连接输入层和隐含层，synapse_HO用来连接隐含层和输出层
		 */
		// Now create the two Synapses
		FullSynapse synapse_IH = new FullSynapse(); /* input -> hidden conn. */
		FullSynapse synapse_HO = new FullSynapse(); /* hidden -> output conn. */

		// Connect the input layer whit the hidden layer
		input.addOutputSynapse(synapse_IH);
		hidden.addInputSynapse(synapse_IH);

		// Connect the hidden layer whit the output layer
		hidden.addOutputSynapse(synapse_HO);
		output.addInputSynapse(synapse_HO);

		/**
		 * 文件读取
		 */
		fileInputSynapse = new FileInputSynapse();
		input.addInputSynapse(fileInputSynapse);
		fileDisireOutputSynapse = new FileInputSynapse();
		TeachingSynapse trainer = new TeachingSynapse();
		trainer.setDesired(fileDisireOutputSynapse);

		// Now we add this structure to a NeuralNet object
		/**
		 * 将之前初始化的构件连接成一个神经网络，NeuralNet是JOONE提供的类，主要是连接各个神经层
		 * 最后一个nnet.addNeuralNetListener(this);这个作用是对神经网络的训练过程进行监听
		 * 因为这个类实现了NeuralNetListener这个接口，这个接口有一些方法，可以实现观察神经网络训练过程，有助于参数调整
		 */
		nnet = new NeuralNet();

		nnet.addLayer(input, NeuralNet.INPUT_LAYER);
		nnet.addLayer(hidden, NeuralNet.HIDDEN_LAYER);
		nnet.addLayer(output, NeuralNet.OUTPUT_LAYER);
		nnet.setTeacher(trainer);
		output.addOutputSynapse(trainer);
		nnet.addNeuralNetListener(this);
	}

	/**
	 * Method declaration
	 */
	@SuppressWarnings("deprecation")
	public void train() {
		/**
		 * 这个方法是初始化输入层数据，也就是指定输入层数据的内容
		 * inputArray是程序中给定的二维数组，这也就是为什么之前初始化神经网络的时候使用的是MemoryInputSynapse
		 */
		fileInputSynapse.setInputFile(inputFile);
		fileInputSynapse.setFirstCol(INSTARTCOL);// 使用文件的第2列到第3列作为输出层输入
		fileInputSynapse.setLastCol(INEDNCOL);

		fileDisireOutputSynapse.setInputFile(inputFile2);
		fileDisireOutputSynapse.setFirstCol(DISSTARTCOL);// 使用文件的第1列作为输出数据
		fileDisireOutputSynapse.setLastCol(DISENDCOL);

		/**
		 * monitor类也是JOONE框架提供的，主要是用来调节神经网络的参数
		 */
		// get the monitor object to train or feed forward
		Monitor monitor = nnet.getMonitor();

		// set the monitor parameters
		// 是用来设置神经网络训练的步长参数，步长越大，神经网络梯度下降的速度越快
		monitor.setLearningRate(0.8);
		monitor.setMomentum(0.3);
		// 这个是设置神经网络的输入层的训练数据大小size，这里使用的是数组的长度；
		// monitor.setTrainingPatterns(inputArray.length);
		monitor.setTrainingPatterns(SIZE);
		// 这个指的是设置迭代数目
		monitor.setTotCicles(5000);
		// 这个true表示是在训练过程
		monitor.setLearning(true);

		long initms = System.currentTimeMillis();
		// Run the network in single-thread, synchronized mode
		nnet.getMonitor().setSingleThreadMode(singleThreadMode);
		// 开始训练
		nnet.go(true);
		System.out.println(" Total time=  " + (System.currentTimeMillis() - initms) + "  ms ");
	}

	/**
	 * 询问 相当于测试方法
	 */
	@SuppressWarnings("deprecation")
	private void interrogate() {
		// nnet = loadFromFile();
		// fileInputSynapse = loadFromSynapse();

		NeuralNetLoader loader = new NeuralNetLoader(SAVENNET);
		NeuralNet nnet = loader.getNeuralNet();

	
		fileInputSynapse.setInputFile(testInputFile);
		fileInputSynapse.setFirstCol(TESTSTARTCOL);// 使用文件的第2列到第3列作为输出层输入
		fileInputSynapse.setLastCol(TESTENDCOL);

		Monitor monitor = nnet.getMonitor();

		// monitor.setTrainingPatterns(4);这个是指测试的数量，4表示有4个测试数据（虽然这里只有一个）
		monitor.setTrainingPatterns(TESTNUM);
		monitor.setTotCicles(1);
		// 需要设置monitor.setLearning(false);，因为这不是训练过程，并不需要学习
		monitor.setLearning(false);

		nnet.setMonitor(monitor);

		/**
		 * 这里还给nnet添加了一个输出层数据对象，这个对象mmOut是初始测试结果，
		 * 注意到之前我们初始化神经网络的时候并没有给输出层指定数据对象， 因为那个时候我们在训练，而且指定了trainer作为目的输出。
		 */
		MemoryOutputSynapse memOut = new MemoryOutputSynapse();
		// set the output synapse to write the output of the net

		if (nnet != null) {
			nnet.addOutputSynapse(memOut);
			System.out.println(nnet.check());
			nnet.getMonitor().setSingleThreadMode(singleThreadMode);
			nnet.go();

			for (int i = 0; i < 4; i++) {
				// 输出结果数据了，pattern的个数和输出层的神经元个数一样大，这里输出层神经元的个数是1，所以pattern大小为1
				double[] pattern = memOut.getNextPattern();
				System.out.println(" Output pattern # " + (i + 1) + " = " + pattern[0]);
			}
			System.out.println(" Interrogating Finished ");
		}
	}

	// 每个循环结束后输出的信息
	public void cicleTerminated(NeuralNetEvent e) {
	}

	// 神经网络错误率变化时候输出的信息
	public void errorChanged(NeuralNetEvent e) {
		Monitor mon = (Monitor) e.getSource();
		if (mon.getCurrentCicle() % 100 == 0)
			System.out.println(
					" Epoch:  " + (mon.getTotCicles() - mon.getCurrentCicle()) + "  RMSE: " + mon.getGlobalError());
	}

	// 神经网络开始运行的时候输出的信息
	public void netStarted(NeuralNetEvent e) {
		Monitor mon = (Monitor) e.getSource();
		System.out.print(" Network started for  ");
		if (mon.isLearning())
			System.out.println(" training. ");
		else
			System.out.println(" interrogation. ");
	}

	// 神经网络停止的时候输出的信息
	public void netStopped(NeuralNetEvent e) {
		Monitor mon = (Monitor) e.getSource();
		System.out.println(" Network stopped. Last RMSE= " + mon.getGlobalError());
		if (mon.isLearning()) {
			saveModel(nnet); // 序列化对象
			saveFile(fileInputSynapse);
		}
		System.out.println("Save Finished");
	}

	private void saveModel(NeuralNet nnet) {
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(SAVENNET));
			os.writeObject(nnet);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveFile(FileInputSynapse nnet) {
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(SAVEFILE));
			os.writeObject(nnet);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static NeuralNet loadFromFile() {
		NeuralNet nnetFromFile = null;
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(SAVENNET));
			nnetFromFile = (NeuralNet) is.readObject();// 从流中读取
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return nnetFromFile;
	}

	private static FileInputSynapse loadFromSynapse() {
		FileInputSynapse nnetFromFile = null;
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(SAVEFILE));
			nnetFromFile = (FileInputSynapse) is.readObject();// 从流中读取
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return nnetFromFile;
	}

	public void netStoppedError(NeuralNetEvent e, String error) {
		System.out.println(" Network stopped due the following error:  " + error);
	}

}