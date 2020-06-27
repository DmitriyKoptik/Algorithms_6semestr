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
	    	Main.log("Closeness: "+closeness);
		
		return  closeness!= 0  ? 1/(float)closeness : Main.TARGET_IS_REACHED_FLAG ;
	}




public Chromosome mutateWithGivenLikelihood(){
       Main.log( "Starting mutateWithGivenLikelihood().... Diofant.MUTATION_LIKELIHOOD=="+ Main.MUTATION_LIKELIHOOD );
    
    Chromosome result =  (Chromosome ) this.clone();

    for (int i = 0; i< Main.GENES_COUNT; ++i){
	    float randomPercent = Main.getRandomFloat(0,100);
	    if ( randomPercent < Main.MUTATION_LIKELIHOOD ){
	         int oldValue =  result.getGenes()[i];
	         int newValue= 	Main.getRandomGene();
 	         Main.log( "Performing mutation for gene #"+i
			+". ( randomPercent =="+randomPercent 
			+" ). Old value:"+oldValue +"; New value:"+newValue ); 	
                         result.getGenes()[i] = newValue;

	       
	   }
    }	
           Main.log( "Finished mutateWithGivenLikelihood()...." );
           return result;		
}


	public Chromosome[] doubleCrossover(  Chromosome chromosome  ){
		
        Main.log( "Starting DOUBLE crossover operation..." );
        Main.log( "THIS chromo:"+this );
        Main.log( "ARG chromo:"+chromosome );
        
        
		
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

		Main.log( "RESULTING chromo #0:\n"+result[0] );
		Main.log( "RESULTING chromo #1:\n"+result[1] );
		Main.log( "DOUBLE crossover operation is finished..." );

                                 return result;

	}



public Chromosome singleCrossover(  Chromosome chromosome  ){
   Main.log( "Starting SINGLE crossover operation...Calling DOUBLE crossover first...." );
   Chromosome[] children = doubleCrossover(  chromosome  ) ;
    Main.log( "Selecting ONE of the 2 children returned by DOUBLE crossover ...." );
     int childNumber = Main.getRandomInt(0, 1);
    Main.log( "Selected child #"+childNumber +", here it is:\n"+children[childNumber] );
   Main.log( "SINGLE crossover operation is finished" );
    return children[childNumber];
}


	public boolean equals( Chromosome chromosome ){
		
		for (int i = 0; i< Main.GENES_COUNT; ++i ){
		  if ( this.genes[i]!=chromosome.genes[i]  ){
			  return false;
		  }
		}
		return true;
		
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
		result.append( "Likelihood:" + likelihood+"\n" );
		
		
		return result.toString();
		
		
	}


	private static int getRandomCrossoverLine(){
		int line = Main.getRandomInt(0, Main.GENES_COUNT - 2);  //-2 because we dn't need the position after the last gene
		Main.log("Generated random CrossoverLine at position "+line);
		return line;
	}
	

	protected Object clone()  {
		Chromosome resultChromosome = new Chromosome() ;
		resultChromosome.setFitness(  this.getFitness() );
		resultChromosome.setLikelihood(  this.getLikelihood() );
		
		int resultGenes[]=new int[Main.GENES_COUNT];
		resultGenes=this.genes.clone();   //???? is this a correct way ?????
		
		resultChromosome.setGenes(resultGenes);
	
		return resultChromosome;
	}


	public static void main(String[] args) throws Exception{
        
		System.out.print( Integer.toBinaryString( 2 )  );

        
	}

}
