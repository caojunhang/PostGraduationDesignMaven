package cn.edu.ncut.istc.nn.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.joone.engine.FullSynapse;
import org.joone.engine.Layer;
import org.joone.engine.LinearLayer;
import org.joone.engine.Monitor;
import org.joone.engine.NeuralNetEvent;
import org.joone.engine.NeuralNetListener;
import org.joone.engine.SigmoidLayer;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.FileInputSynapse;
import org.joone.io.FileOutputSynapse;
import org.joone.net.NeuralNet;
import org.joone.net.NeuralNetLoader;

public class XOR_Test implements NeuralNetListener, Serializable {

	private static final long serialVersionUID = -3597853311948127352L;
	private FileInputSynapse inputStream = null;
	private NeuralNet nnet = null;

	public static void main(String args[]) {
		XOR_Test xor = new XOR_Test();

		xor.initNeuralNet();
		
		test2();
	}

	protected void initNeuralNet() {

		// 三层：输入层 ，隐层，输出层
		// create the three layers (using the sigmoid transfer function for the
		// hidden and the output layers)
		LinearLayer input = new LinearLayer();
		SigmoidLayer hidden = new SigmoidLayer();
		SigmoidLayer output = new SigmoidLayer();

		// set their dimensions(神经元):
		// 设置每层神经元的个数
		input.setRows(2);
		hidden.setRows(3);
		output.setRows(1);

		// Now build the neural net connecting the layers by creating the two
		// synapses(突触)
		// 三层需要两个突触对其进行连接,创建神经元
		FullSynapse synapse_IH = new FullSynapse(); /* Input -> Hidden conn. */
		FullSynapse synapse_HO = new FullSynapse(); /* Hidden -> Output conn. */

		// 连接操作
		// Next connect the input layer with the hidden layer:
		input.addOutputSynapse(synapse_IH);
		hidden.addInputSynapse(synapse_IH);
		// and then, the hidden layer with the output layer:
		hidden.addOutputSynapse(synapse_HO);
		output.addInputSynapse(synapse_HO);

		// need a NeuralNet object that will contain all the Layers of the
		// network
		// 创建一个网络来容纳各层
		nnet = new NeuralNet();
		nnet.addLayer(input, NeuralNet.INPUT_LAYER);
		nnet.addLayer(hidden, NeuralNet.HIDDEN_LAYER);
		nnet.addLayer(output, NeuralNet.OUTPUT_LAYER);

		Monitor monitor = nnet.getMonitor();
		// 设定神经网络的学习率，
		monitor.setLearningRate(0.8);
		// 设定神经网络的动量 为 0.3 这两个变量与步长有关
		monitor.setMomentum(0.3);
		monitor.addNeuralNetListener(this);

		// 输入流
		inputStream = new FileInputSynapse();
		/* The first two columns contain the input values */
		inputStream.setAdvancedColumnSelector("1,2,3,4");
		/* This is the file that contains the input data */
		inputStream.setInputFile(new File("C:\\nn\\j\\input.txt"));
		// Next add the input synapse to the first layer.
		input.addInputSynapse(inputStream);

		TeachingSynapse trainer = new TeachingSynapse();
		/*
		 * Setting of the file containing the desired responses, provided by a
		 * FileInputSynapse
		 */
		FileInputSynapse samples = new FileInputSynapse();
		samples.setInputFile(new File("C:\\nn\\j\\input2.txt"));
		/* The output values are on the third column of the file */
		samples.setAdvancedColumnSelector("1");
		trainer.setDesired(samples);

		output.addOutputSynapse(trainer);
		/* We attach the teacher to the network */
		nnet.setTeacher(trainer);

		monitor.setTrainingPatterns(4); /* # of rows in the input file */
		monitor.setTotCicles(8000); /* How many times the net must be trained */

		monitor.setLearning(true); /* The net must be trained */
		nnet.go(); /* The network starts the training phase */

	}

	@Override
	public void netStarted(NeuralNetEvent e) {
		System.out.println("Training...");

	}

	@Override
	public void cicleTerminated(NeuralNetEvent e) {
		Monitor mon = (Monitor) e.getSource();
		long c = mon.getCurrentCicle();
		/* We want print the results every 100 epochs */
		if (c % 100 == 0)
			System.out.println(c + " epochs remaining - RMSE = " + mon.getGlobalError());

	}

	@Override
	public void netStopped(NeuralNetEvent e) {
		System.out.println("Training Stopped...");
		long mystr = System.currentTimeMillis(); // 初始化当前的系统时间
		System.out.println(mystr);

		saveNeuralNet("c://nn//j//xor.snet"); // 保存生成当前时间的myxor.snet神经网络
		//test();

	}

	public void test() {
		NeuralNet xorNNet = this.restoreNeuralNet("D://xor.snet");
		if (xorNNet != null) {
			// we get the output layer
			Layer output = xorNNet.getOutputLayer();
			// we create an output synapse
			FileOutputSynapse fileOutput = new FileOutputSynapse();
			fileOutput.setFileName("d://xor_out.txt");
			// we attach the output synapse to the last layer of the NN
			output.addOutputSynapse(fileOutput);
			// we run the neural network only once (1 cycle) in recall mode
			xorNNet.getMonitor().setTotCicles(1);
			xorNNet.getMonitor().setLearning(false);
			xorNNet.go();
		}
	}

	
	
	public static void test2() {
		NeuralNet xorNNet = restoreNeuralNet("c://nn//j//xor.snet");
		if (xorNNet != null) {
			// we get the output layer
			Layer output = xorNNet.getOutputLayer();
			// we create an output synapse
			FileOutputSynapse fileOutput = new FileOutputSynapse();
			fileOutput.setFileName("d://xor_out.txt");
			// we attach the output synapse to the last layer of the NN
			output.addOutputSynapse(fileOutput);
			// we run the neural network only once (1 cycle) in recall mode
			xorNNet.getMonitor().setTotCicles(1);
			xorNNet.getMonitor().setLearning(false);
			xorNNet.go();
		}
	}
	
	
	@Override
	public void errorChanged(NeuralNetEvent e) {
		Monitor mon = (Monitor) e.getSource();// 得到监控层的信息
		long c = mon.getCurrentCicle();
		if (c % 100 == 0)
			// 输出
			System.out.println(
					"Cycle: " + (mon.getTotCicles() - mon.getCurrentCicle()) + " RMSE:" + mon.getGlobalError());
	}

	public void saveNeuralNet(String fileName) {
		try {
			FileOutputStream stream = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(stream);
			out.writeObject(nnet);// 写入nnet对象
			out.close();
		} catch (Exception excp) {
			excp.printStackTrace();
		}
	}

	static NeuralNet restoreNeuralNet(String fileName) {
		NeuralNetLoader loader = new NeuralNetLoader(fileName);
		NeuralNet nnet = loader.getNeuralNet();
		return nnet;
	}

	public void netStoppedError(NeuralNetEvent e, String error) {
		// TODO Auto-generated method stub

	}

}
