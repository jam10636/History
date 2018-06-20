package spell;

/**
 * Created by KevinTsou on 2/4/2018.
 */

public class spelltest implements ITrie {
    Node root=new Node();
    int wordcount=0;
    int nodecount=1;
    public void add(String word) {
        Node current=root;
        for(int i=0;i< word.length();i++)
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
                if(current.count==0)
                {
                    wordcount++;
                }
                current.count++;
            }
        }
    }
    public INode find(String word) {
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
                    else {
                        return current;
                    }
                }
            }
        }
        return null;
    }
    public int getWordCount() {
        return wordcount;
    }
    public int getNodeCount() {
        return nodecount;
    }
    @Override
    public String toString() {
        StringBuilder output=new StringBuilder();
        StringBuilder word=new StringBuilder();
        toString2(output,word,root);
        return output.toString();

    }
    public void toString2(StringBuilder output,StringBuilder word,Node in)
    {
        if(in.count!=0)
        {
            output.append(word+"\n");
        }
        for(int i=0;i<26;i++){
            if(in.node[i]!=null)
            {
                word.append((char)(i+'a'));
                toString2(output,word,in.node[i]);
                word.deleteCharAt(word.length()-1);
            }
        }

    }
    @Override
    public int hashCode() {
        return 31*wordcount*nodecount;
    }
    @Override
    public boolean equals(Object o) {
        if(o==null)
        {
            return false;
        }
        else if(o.getClass()!=getClass())
        {
            return false;
        }
        spelltest other=(spelltest)o;
        if(other.nodecount!=this.nodecount)
        {
            return false;
        }
        else if(other.wordcount!=this.wordcount)
        {
            return false;
        }
        return equals2(root,other.root);
    }
    public boolean equals2(Node in,Node out)
    {
        for(int i=0;i<26;i++)
        {
            if(in.node[i]!=null&&out.node[i]!=null)
            {
                if(in.node[i].count!=out.node[i].count)
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
            if((in.node[i]==null&&in.node[i]!=null)||(in.node[i]!=null&&out.node[i]==null))
            {
                return false;
            }
        }
        return true;
    }
             public class Node implements ITrie.INode {
                 public Node[]node=new Node[26];
                 public int count=0;
             		public int getValue() {
             		    return count;
             		}
	  }

}
