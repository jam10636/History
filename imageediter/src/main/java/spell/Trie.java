package spell;

/**
 * Created by KevinTsou on 1/20/2018.
 */

public class Trie implements ITrie {
    private int nodecount=1;
    private int wordcount=0;
    Node root=new Node();
    public void add(String word)
    {
        Node current=root;
        for(int i=0;i<word.length();i++)
        {
            if(current.node[word.charAt(i)-'a']==null)
            {
                current.node[word.charAt(i)-'a']=new Node();
                current=current.node[word.charAt(i)-'a'];
                nodecount++;
            }
            else
            {
                current=current.node[word.charAt(i)-'a'];
            }
            if(i==word.length()-1)
            {
                if(current.count==0){
                    wordcount++;
                }
                current.count++;
            }
        }

    }

    public INode find(String word)
    {
        Node current=root;
        for(int i=0;i<word.length();i++)
        {
            if(current.node[word.charAt(i)-'a']==null)
            {
                return null;
            }
            else
            {
                current=current.node[word.charAt(i)-'a'];
                if(i==word.length()-1)
                {
                    if(current.count==0)
                    {
                        return null;
                    }
                    else
                    {
                        return current;
                    }
                }
            }
        }

        return null;
    }

    public int getWordCount()
    {
        return wordcount;
    }


    public int getNodeCount()
    {
        return nodecount;
    }


    @Override
    public String toString()
    {
        StringBuilder output=new StringBuilder();
        StringBuilder word=new StringBuilder();
        toString2(root,output,word);
        return output.toString();
    }
    public void toString2(Node in,StringBuilder output1,StringBuilder word1)
    {
        if(in.count!=0)
        {   output1.append(word1);
            output1.append('\n');
        }
        for(int i=0;i<26;i++) {
            if (in.node[i] != null) {
                // int x=current.node[word.charAt(i)-'a'];
                word1.append((char) ('a' + i));
                toString2(in.node[i], output1, word1);
                word1.deleteCharAt(word1.length() - 1);
            }
        }
    }

    @Override
    public int hashCode()
    {
        return 31*nodecount*wordcount;
    }

    @Override
    public boolean equals(Object o)
    {

		if (o == null) {
            return false;
        }
		else if (getClass() != o.getClass()) {
            return false;
        }
		Trie other = (Trie)o;
		if (this.nodecount!=((Trie) o).nodecount)
        {
            return false;
        }
        else if(this.wordcount!=((Trie) o).wordcount)
        {
            return false;
        }


        return equals2(this.root,((Trie) o).root);

    }
    public boolean equals2(Node in,Node out)
    {
        for(int i=0;i<26;i++)
        {
           if(in.node[i]!=null&&out.node[i]!=null)
            {

                if(in.node[i].getValue()!=out.node[i].getValue())
                {
                    return false;
                }
                else
                {

                    if(equals2(in.node[i],out.node[i])==false)
                    {
                        return false;
                    }
                }
            }
            if((in.node[i]==null&&out.node[i]!=null)||(in.node[i]!=null&&out.node[i]==null))
            {
               return false;
            }
        }

        return true;
    }
    public static class Node implements ITrie.INode
    {
       public int count=0;
        public Node[] node=new Node[26];
        public int getValue()
        {
            return count;
        }
    }

}
