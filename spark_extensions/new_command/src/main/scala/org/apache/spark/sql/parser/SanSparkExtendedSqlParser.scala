/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.sql.parser

import scala.collection.mutable
import scala.language.implicitConversions
import scala.util.parsing.combinator.lexical.StdLexical
import scala.util.parsing.combinator.syntactical.StandardTokenParsers

import org.apache.spark.sql.PrintCommand
import org.apache.spark.sql.catalyst.plans.logical._


/**
 * Parser for DDL command "PRINTME <somestring>"
 */
class SanSparkExtendedSqlParser extends StandardTokenParsers {

  lexical.reserved += ("PRINTME")

  def parse(input: String): LogicalPlan = {
    synchronized {
      // Initialize the Keywords.
	  val tokens = new lexical.Scanner(input)
      phrase(start)(tokens) match {
        case Success(plan, _) => plan 
        case failureOrError =>
          sys.error(failureOrError.toString)
      }
    }
  }

  protected lazy val start: Parser[LogicalPlan] = printCommand

  protected lazy val printCommand: Parser[LogicalPlan] =
    "PRINTME" ~> ident ^^ {
	  case ident => PrintCommand(ident)
	}
}

