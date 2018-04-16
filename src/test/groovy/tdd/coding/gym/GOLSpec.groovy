package tdd.coding.gym

import org.springframework.boot.test.rule.OutputCapture
import spock.lang.Specification
import spock.lang.Unroll

class GOLSpec extends Specification {

    def "A new GOL has a populationSize of zero"() {
        given:
        def gol = new GOL();
        expect:
        gol.populationSize() == 0;
    }

    @Unroll
    def "Populating with #xys gives census #expectedCells"() {
        given:
        def gol = new GOL();

        expect:
        gol.populate(xys ).cells.equals( expectedCells )

        where:
        xys       || expectedCells
        "1,1" ||  [ new Cell(1,1) ] as Set
        "1,1, 2,2" ||  [ new Cell(1,1), new Cell(2,2) ] as Set
        "1,1, 1,1" ||  [ new Cell(1,1)] as Set
     }

    @Unroll
    def "Populating with #xys evolves to #census"() {
        given:
        def gol = new GOL();

        expect:
        gol.populate(xys).evolve().census() == census

        where:
        xys   || census
        "5,5, 6,5, 7,5" ||  [ new Cell(6,5), new Cell(6,6), new Cell(6,4) ] as Set

    }

    @org.junit.Rule
    OutputCapture capture = new OutputCapture()

    def "GOL Display ok"(){
        given:
        def gol = new GOL().populate("-1,0, 0,0, 1,0");

        when:
        gol.show(5);

        then:
        capture.toString() ==
                """----------
----------
----------
----------
----------
----+++---
----------
----------
----------
----------
----------
==========================
"""
    }

    def "Play Game"(){
        given:
        String[] repeats = ["10"];

        when:
        GOL.main( repeats );

        then:
        capture.toString().contains("+++");
    }
}