package eapli.base.messagemanagement.domain;

public enum Type {
    CONSUMO, ENTREGA_PRODUCAO, PRODUCAO, ESTORNO, INICIO_ACTIVIDADE, RETOMA_ATIVIDADE, PARAGEM, FIM_ATIVIDADE;


/*    CONSUMO {
        private Product p;
        private int quantity;
        private Deposit d;

        @Override
        public void setValues(List<Object> lst) {
            p= (Product) lst.get(0);
            quantity = (int) lst.get(1);
            d = (Deposit) lst.get(2);
        }
    }, ENTREGA_PRODUCAO {
        private Product p;
        private int quantity;
        private Deposit d;

    }, PRODUCAO {
        private Product p;
        private int quantity;
        private Batch b;
    }, ESTORNO {
        private Product p;
        private int quantity;
        private Deposit d;

    }, INICIO_ACTIVIDADE {
        private ProductionOrder po;

    }, RETOMA_ACTIVIDADE {
        //error
    }, PARAGEM {
        //nothing
    }, FIM_ATIVIDADE {
        private ProductionOrder po;

        public void setPo(ProductionOrder po) {
            this.po = po;
        }
    };

    public abstract void setValues(List<Object> lst);*/


}
