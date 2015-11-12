/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;

/**
 *
 * @author B25201
 */
public class WeightVectors {
    
    double[] termWeight;
	
	public WeightVectors(int size){
            termWeight = new double[size];
        }
        
        public double getNorm(){
            double sumatory=0;
            for(int i=0;i<termWeight.length;i++){
                sumatory += Math.pow(termWeight[i],2);
            }
            return Math.sqrt(sumatory);
        }
        
        public void normalizeVector(){
            double norm = getNorm();
            for(int i=0;i<termWeight.length;i++){
                termWeight[i] /= norm;
            }
        }
        
        public double getCosineSimilarity(WeightVectors w2){
            double myNorm = this.getNorm();
            double otherNorm = w2.getNorm();
            
            double dotProduct = 0.0;
            for(int i=0;i<this.termWeight.length;i++){
                dotProduct += this.termWeight[i]* w2.termWeight[i];
            }
            return (dotProduct / (myNorm * otherNorm));
        }
}
