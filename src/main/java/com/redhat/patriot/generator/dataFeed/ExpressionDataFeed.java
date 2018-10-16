///*
// * Copyright 2018 Patriot project
// *
// *    Licensed under the Apache License, Version 2.0 (the "License");
// *    you may not use this file except in compliance with the License.
// *    You may obtain a copy of the License at
// *
// *        http://www.apache.org/licenses/LICENSE-2.0
// *
// *    Unless required by applicable law or agreed to in writing, software
// *    distributed under the License is distributed on an "AS IS" BASIS,
// *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *    See the License for the specific language governing permissions and
// *    limitations under the License.
// */
//
//package com.redhat.patriot.generator.device.dateFeed;
//
//import java.util.Set;
//
//import net.objecthunter.exp4j.Expression;
//
///**
// * Created by jsmolar on 9/9/18.
// */
//public class ExpressionDataFeed extends DataFeed {
//
//    private Expression expression;
//
//    private String variable;
//
//    public ExpressionDataFeed(Expression expression) {
//        this.expression = expression;
//        validate();
//    }
//
//    @Override
//    public double getValue(double time) {
//        expression.setVariable(variable, time);
//        double result = expression.evaluate();
//        LOGGER.info("Generated data from expression: " + result);
//
//        return result;
//    }
//
//    private void validate() {
//        if(expression.validate().isValid()) {
//            Set<String> variables = expression.getVariableNames();
//            if(variables.size() != 1) {
//                // Handle invalid expression
//            }
//
//            variable = (String) variables.toArray()[0];
//        } else {
//            // Throw exception
//        }
//    }
//}
//
//
////    public DataFeed(double lambda) {
////        MRG32k3a.setPackageSeed(Seed.generate());
////        RandomStream randomStream = new MRG32k3a();
////
////        randomVariate = new PoissonGen(randomStream, new PoissonDist(lambda));
////    }
