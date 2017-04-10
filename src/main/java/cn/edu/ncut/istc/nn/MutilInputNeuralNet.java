package cn.edu.ncut.istc.nn;

import org.joone.engine.*;
import org.joone.engine.learning.*;
import org.joone.io.*;
import org.joone.net.*;

public class MutilInputNeuralNet implements NeuralNetListener {
	private NeuralNet nnet = null;
	private MemoryInputSynapse inputSynapse, desiredOutputSynapse;
	LinearLayer input;
	SigmoidLayer hidden, output;
	boolean singleThreadMode = true;

	// XOR input
	private double[][] inputArray = new double[][] { { 5.0,3.0,1.6,0.2 }, { 5.0,3.4,1.6,0.4 },{5.6,2.7,4.2,1.3},{5.7,3.0,4.2,1.2} };

	// XOR desired output
	private double[][] desiredOutputArray = new double[][] { { 1.0 }, { 1.0 },{ 0.0 },{ 0.0 } };

	/**
	 * @param args
	 *            先初始化
	 *            训练
	 *            测试
	 */
	public static void main(String args[]) {
		MutilInputNeuralNet xor = new MutilInputNeuralNet();

		xor.initNeuralNet();
		xor.train();
		xor.interrogate();
	}

	/**
	 * Method declaration
	 */
	public void train() {
		/**
		 * 这个方法是初始化输入层数据，也就是指定输入层数据的内容
		 * inputArray是程序中给定的二维数组，这也就是为什么之前初始化神经网络的时候使用的是MemoryInputSynapse
		 */
		// set the inputs
		inputSynapse.setInputArray(inputArray);
		// 表示的是输入层数据使用的是inputArray的前两列数据
		inputSynapse.setAdvancedColumnSelector(" 1,2,3,4 ");
		// desiredOutputSynapse同理
		// set the desired outputs
		desiredOutputSynapse.setInputArray(desiredOutputArray);
		desiredOutputSynapse.setAdvancedColumnSelector(" 1 ");

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
		monitor.setTrainingPatterns(inputArray.length);
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
	private void interrogate() {

		// inputArray是测试数据
		//double[][] inputArray = new double[][] {{6.2,2.9,4.3,1.3},{5.7,2.8,4.1,1.3}};//0  0 
		double[][] inputArray = new double[][] {{6.2,2.9,4.3,1.3},{5.0,3.5,1.3,0.3}};//0  1 
		
		// set the inputs
		/**
		 * 这个方法是初始化输入层数据，也就是指定输入层数据的内容
		 * inputArray是程序中给定的二维数组，这也就是为什么之前初始化神经网络的时候使用的是MemoryInputSynapse
		 */
		inputSynapse.setInputArray(inputArray);
		// 表示的是输入层数据使用的是inputArray的前两列数据
		inputSynapse.setAdvancedColumnSelector(" 1,2,3,4 ");

		Monitor monitor = nnet.getMonitor();
		// monitor.setTrainingPatterns(4);这个是指测试的数量，4表示有4个测试数据（虽然这里只有一个）
		monitor.setTrainingPatterns(2);
		monitor.setTotCicles(1);
		// 需要设置monitor.setLearning(false);，因为这不是训练过程，并不需要学习
		monitor.setLearning(false);
		
		/**
		 * 这里还给nnet添加了一个输出层数据对象，这个对象mmOut是初始测试结果，
		 * 注意到之前我们初始化神经网络的时候并没有给输出层指定数据对象，
		 * 因为那个时候我们在训练，而且指定了trainer作为目的输出。
		 */
		MemoryOutputSynapse memOut = new MemoryOutputSynapse();
		// set the output synapse to write the output of the net

		if (nnet != null) {
			nnet.addOutputSynapse(memOut);
			System.out.println(nnet.check());
			nnet.getMonitor().setSingleThreadMode(singleThreadMode);
			nnet.go();

			for (int i = 0; i < 4; i++) {
				//输出结果数据了，pattern的个数和输出层的神经元个数一样大，这里输出层神经元的个数是1，所以pattern大小为1
				double[] pattern = memOut.getNextPattern();
				System.out.println(" Output pattern # " + (i + 1) + " = " + pattern[0]);
			}
			System.out.println(" Interrogating Finished ");
		}
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
		input.setRows(4);  //此参数系统会自动设置  调用Layer的 fireFwdGet中的adjustSizeToFwdPattern
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

		// the input to the neural net
		// 训练的时候指定输入层的数据和目的输出的数据
		// 这里指的是使用了从内存中输入数据的方法，指的是输入层输入数据，可以从文件输入的方法
		inputSynapse = new MemoryInputSynapse();

		input.addInputSynapse(inputSynapse);

		// The Trainer and its desired output
		// 从内存中输入数据，指的是从输入层应该输出的数据
		desiredOutputSynapse = new MemoryInputSynapse();

		// 设置训练
		TeachingSynapse trainer = new TeachingSynapse();

		trainer.setDesired(desiredOutputSynapse);

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

	//每个循环结束后输出的信息
	public void cicleTerminated(NeuralNetEvent e) {
	}

	//神经网络错误率变化时候输出的信息
	public void errorChanged(NeuralNetEvent e) {
		Monitor mon = (Monitor) e.getSource();
		if (mon.getCurrentCicle() % 100 == 0)
			System.out.println(
					" Epoch:  " + (mon.getTotCicles() - mon.getCurrentCicle()) + "  RMSE: " + mon.getGlobalError());
	}

	//神经网络开始运行的时候输出的信息
	public void netStarted(NeuralNetEvent e) {
		Monitor mon = (Monitor) e.getSource();
		System.out.print(" Network started for  ");
		if (mon.isLearning())
			System.out.println(" training. ");
		else
			System.out.println(" interrogation. ");
	}

	//神经网络停止的时候输出的信息
	public void netStopped(NeuralNetEvent e) {
		Monitor mon = (Monitor) e.getSource();
		System.out.println(" Network stopped. Last RMSE= " + mon.getGlobalError());
	}

	public void netStoppedError(NeuralNetEvent e, String error) {
		System.out.println(" Network stopped due the following error:  " + error);
	}

}