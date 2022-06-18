package com.sky.batchprocessing.processor;

import org.springframework.batch.item.ItemProcessor;

import com.sky.batchprocessing.model.InProduct;
import com.sky.batchprocessing.model.OutProduct;

public class ProductProcessor implements ItemProcessor<InProduct, OutProduct>{

	@Override
	public OutProduct process(InProduct product) throws Exception {
		
		final OutProduct transformaedproduct= new OutProduct(product.getName(), product.getPrice(), product.getPrice()*0.9);
		return transformaedproduct;
	}

	
}