package cn.edu.ncut.istc.nn.test;

import java.io.File;

import org.joone.engine.*;
import org.joone.engine.learning.*;
import org.joone.io.*;
import org.joone.net.*;

public class InputNeuralNet implements NeuralNetListener {
	private NeuralNet nnet = null;
	private MemoryInputSynapse inputSynapse, desiredOutputSynapse;
	private FileInputSynapse fileInputSynapse,fileDisireOutputSynapse;
	
	private File inputFile = new File("C:\\nn\\input.txt");  
	private File inputFile2 = new File("C:\\nn\\input2.txt");  
	private File testInputFile = new File("C:\\nn\\test.txt");  

	LinearLayer input;
	SigmoidLayer hidden, output;
	boolean singleThreadMode = true;

	// XOR input
//	private double[][] inputArray = new double[][] { { 5.0, 3.0, 1.6, 0.2 }, { 5.2, 4.1, 1.5, 0.1 },
//			{ 5.1, 3.4, 1.5, 0.2 }, { 5.1, 3.8, 1.6, 0.2 }, { 6.0, 2.9, 4.5, 1.5 }, { 5.5, 2.4, 3.8, 1.1 },
//			{ 5.6, 3.0, 4.1, 1.3 }, { 5.5, 2.5, 4.0, 1.3 }, };
//
//	// XOR desired output
//	private double[][] desiredOutputArray = new double[][] { { 0.0 }, { 0.0 }, { 0.0 }, { 0.0 }, { 1.0 }, { 1.0 },
//			{ 1.0 }, { 1.0 } };

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		InputNeuralNet xor = new InputNeuralNet();

		xor.initNeuralNet();
		xor.train();
		xor.interrogate();
	}

	/**
	 * Method declaration
	 */
	public void train() {

		
		fileInputSynapse.setInputFile(inputFile);  
		fileInputSynapse.setFirstCol(1);//使用文件的第2列到第3列作为输出层输入  
		fileInputSynapse.setLastCol(4);  
		
		fileDisireOutputSynapse.setInputFile(inputFile2);  
		fileDisireOutputSynapse.setFirstCol(1);//使用文件的第1列作为输出数据  
		fileDisireOutputSynapse.setLastCol(1);  
		
//		// set the inputs
//		inputSynapse.setInputArray(inputArray);
//		inputSynapse.setAdvancedColumnSelector(" 1,2,3,4 ");
//		// set the desired outputs
//		desiredOutputSynapse.setInputArray(desiredOutputArray);
//		desiredOutputSynapse.setAdvancedColumnSelector(" 1 ");

		// get the monitor object to train or feed forward
		Monitor monitor = nnet.getMonitor();

		// set the monitor parameters
		monitor.setLearningRate(0.8);
		monitor.setMomentum(0.3);
		monitor.setTrainingPatterns(8);
		monitor.setTotCicles(5000);
		monitor.setLearning(true);

		long initms = System.currentTimeMillis();
		// Run the network in single-thread, synchronized mode
		nnet.getMonitor().setSingleThreadMode(singleThreadMode);
		nnet.go(true);
		System.out.println(" Total time=  " + (System.currentTimeMillis() - initms) + "  ms ");
	}

	@SuppressWarnings("deprecation")
	private void interrogate() {
//		double[][] inputArray = new double[][] { { 4.9, 3.1, 1.5, 0.1 }, { 4.8, 3.0, 1.4, 0.3 }, { 5.0, 2.3, 3.3, 1.0 },
//				{ 5.7, 2.9, 4.2, 1.3 }
//		};
//		// set the inputs
//		inputSynapse = new MemoryInputSynapse();
//		inputSynapse.setInputArray(inputArray);
//		inputSynapse.setAdvancedColumnSelector(" 1,2,3,4 ");
		
		fileInputSynapse.setInputFile(testInputFile);  
		fileInputSynapse.setFirstCol(1);//使用文件的第2列到第3列作为输出层输入  
		fileInputSynapse.setLastCol(4);  
		
		
		Monitor monitor = nnet.getMonitor();
		monitor.setTrainingPatterns(4);
		monitor.setTotCicles(1);
		monitor.setLearning(false);
		MemoryOutputSynapse memOut = new MemoryOutputSynapse();
		// set the output synapse to write the output of the net

		if (nnet != null) {
			nnet.addOutputSynapse(memOut);
			System.out.println(nnet.check());
			nnet.getMonitor().setSingleThreadMode(singleThreadMode);
			nnet.go();

			for (int i = 0; i < 4; i++) {
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

		// First create the three layers
		input = new LinearLayer();
		hidden = new SigmoidLayer();
		output = new SigmoidLayer();

		// set the dimensions of the layers
		input.setRows(4);
		hidden.setRows(3);
		output.setRows(1);

		input.setLayerName(" L.input ");
		hidden.setLayerName(" L.hidden ");
		output.setLayerName(" L.output ");

		// Now create the two Synapses
		FullSynapse synapse_IH = new FullSynapse(); /* input -> hidden conn. */
		FullSynapse synapse_HO = new FullSynapse(); /* hidden -> output conn. */

		// Connect the input layer whit the hidden layer
		input.addOutputSynapse(synapse_IH);
		hidden.addInputSynapse(synapse_IH);

		// Connect the hidden layer whit the output layer
		hidden.addOutputSynapse(synapse_HO);
		output.addInputSynapse(synapse_HO);

//		// the input to the neural net
//		inputSynapse = new MemoryInputSynapse();
//
//		input.addInputSynapse(inputSynapse);
//
//		// The Trainer and its desired output
//		desiredOutputSynapse = new MemoryInputSynapse();
//
//		TeachingSynapse trainer = new TeachingSynapse();
//
//		trainer.setDesired(desiredOutputSynapse);

		
		fileInputSynapse = new FileInputSynapse();  
		input.addInputSynapse(fileInputSynapse);  
		fileDisireOutputSynapse = new FileInputSynapse();  
		TeachingSynapse trainer = new TeachingSynapse();  
		trainer.setDesired(fileDisireOutputSynapse);  
		
		// Now we add this structure to a NeuralNet object
		nnet = new NeuralNet();

		nnet.addLayer(input, NeuralNet.INPUT_LAYER);
		nnet.addLayer(hidden, NeuralNet.HIDDEN_LAYER);
		nnet.addLayer(output, NeuralNet.OUTPUT_LAYER);
		nnet.setTeacher(trainer);
		output.addOutputSynapse(trainer);
		nnet.addNeuralNetListener(this);
	}

	public void cicleTerminated(NeuralNetEvent e) {
	}

	public void errorChanged(NeuralNetEvent e) {
		Monitor mon = (Monitor) e.getSource();
		if (mon.getCurrentCicle() % 100 == 0)
			System.out.println(
					" Epoch:  " + (mon.getTotCicles() - mon.getCurrentCicle()) + "  RMSE: " + mon.getGlobalError());
	}

	public void netStarted(NeuralNetEvent e) {
		Monitor mon = (Monitor) e.getSource();
		System.out.print(" Network started for  ");
		if (mon.isLearning())
			System.out.println(" training. ");
		else
			System.out.println(" interrogation. ");
	}

	public void netStopped(NeuralNetEvent e) {
		Monitor mon = (Monitor) e.getSource();
		System.out.println(" Network stopped. Last RMSE= " + mon.getGlobalError());
	}

	public void netStoppedError(NeuralNetEvent e, String error) {
		System.out.println(" Network stopped due the following error:  " + error);
	}

}