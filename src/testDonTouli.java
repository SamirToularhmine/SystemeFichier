public class testDonTouli {

    public static void main(String[] args) {
        FileParser fp = new FileParser("arbo");
        fp.parserFichier();
        /*try {
            ArbreFichiers root = new ArbreFichiers(null, null, null, null, "root");
            System.out.println(root);
            ArbreFichiers sRoot = new ArbreFichiers(root, null, null, null, "sRoot");
            root.setPremierFils(sRoot);
            System.out.println(sRoot);
            ArbreFichiers s2root = new ArbreFichiers(root,null,sRoot,null,"s2root");
            ArbreFichiers sRootSame = new ArbreFichiers(root, null, null, null, "sR1oot");
            sRoot.setFrereDroit(s2root);
            root.addNode(sRootSame);
            System.out.println(root);
            System.out.println("sRootSame = "+ sRootSame);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }*/


    }

}
