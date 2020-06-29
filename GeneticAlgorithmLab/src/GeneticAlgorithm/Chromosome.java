package GeneticAlgorithm;

public class Chromosome {

	private int genes[]=new int[Main.GENES_COUNT];

	private float fitness;

	private float likelihood;

	public float getFitness() {
		return fitness;
	}
	public void setFitness(float fitness) {
		this.fitness = fitness;
	}
	public int[] getGenes() {
		return genes;
	}
	public void setGenes(int[] genes) {
		this.genes = genes;
	}
	public float getLikelihood() {
		return likelihood;
	}
	public void setLikelihood(float likelihood) {
		this.likelihood = likelihood;
	}
	


	public float  calculateFitness(int target_value){
		int u = genes[0];
		int w = genes[1];
		int x = genes[2];
		int y = genes[3];
		int z = genes[4];
		
		int closeness = Math.abs(   Main.function( u, w, x, y, z ) - target_value   ) ;
		
		return  closeness!= 0  ? 1/(float)closeness : Main.TARGET_IS_REACHED_FLAG ;
	}


	public Chromosome mutation(){
    
    Chromosome result =  (Chromosome ) this.clone();
		if (result.getFitness() == Main.LeastFittest){
			for (int i = 0; i< Main.GENES_COUNT; ++i){
	    	float randomPercentGood = Main.getRandomFloat(0,100);
				if ( randomPercentGood < Main.P ) {
					int oldValue = result.getGenes()[i];
					int newValue = Main.getRandomGene();
					result.getGenes()[i] = newValue;
				}
			}
		}
    return result;
}

	public Chromosome[] doubleCrossover(  Chromosome chromosome  ){
		
		int crossoverline=getRandomCrossoverLine();
		Chromosome[] result = new Chromosome[2];
		result[0]=new Chromosome();
		result[1]=new Chromosome();

		for (int i = 0; i< Main.GENES_COUNT; ++i){
                                    if ( i<=crossoverline){
			result[0].getGenes()[i] =  this.getGenes()[i];
			result[1].getGenes()[i] =  chromosome.getGenes()[i];

	                    }
		    
		   else {
			result[0].getGenes()[i] =  chromosome.getGenes()[i];
			result[1].getGenes()[i] =  this.getGenes()[i]; 			
		    }

		}

		return result;

	}



	public Chromosome singleCrossover(  Chromosome chromosome  ){
     Chromosome[] children = doubleCrossover(  chromosome  ) ;
     int childNumber = Main.getRandomInt(0, 1);
    return children[childNumber];
}



	public String toString(){
		
		StringBuffer result = new StringBuffer();
		
		result.append(  "Genes: ("  ) ;
		
		for (int i = 0; i< Main.GENES_COUNT; ++i ){
			result.append(  ""+genes[i]  ) ;
			result.append(  i< Main.GENES_COUNT-1 ? ", ":""   );
		  	
		}
		
		result.append(  ")\n"  ) ;
		
		result.append( "Fitness:" + fitness+"\n" );
		
		
		return result.toString();
		
		
	}


	private static int getRandomCrossoverLine(){
		int line = Main.getRandomInt(0, Main.GENES_COUNT - 2);
		return line;
	}
	

	protected Object clone()  {
		Chromosome resultChromosome = new Chromosome() ;
		resultChromosome.setFitness(  this.getFitness() );
		resultChromosome.setLikelihood(  this.getLikelihood() );
		
		int resultGenes[]=new int[Main.GENES_COUNT];
		resultGenes=this.genes.clone();
		
		resultChromosome.setGenes(resultGenes);
	
		return resultChromosome;
	}


	public static void main(String[] args) throws Exception{
        
		System.out.print( Integer.toBinaryString( 2 )  );

        
	}

}
