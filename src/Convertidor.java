import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Convertidor
{
    private  Map<Character, Integer> precedenciaOperadores;
        
    public Convertidor()
    {
        Map<Character, Integer> map = new HashMap<>();
        map.put('(', 1); // parentesis
        map.put('|', 2); // Unión
        map.put('.', 3); // Concatenación
        map.put('*', 4); // Operador Clausura de Kleene
        precedenciaOperadores = Collections.unmodifiableMap(map);
    }
    
    /**
    * Obtener la precedencia del caracter
    * 
    * @param c character
    * @return corresponding precedence
    */
    private Integer getPrecedencia(Character c) 
    {
        Integer precedencia = precedenciaOperadores.get(c);
        if(precedencia==null)
        {
            precedencia=6;
        }
        return precedencia;
    }
    
    /**
    * Convertir una expresión regular 
    * 
    * @param expresion notacion infijo 
    * @return notacion postfijo
    */
    public String conversor(String expresion) 
    {
        String postfix = new String();
        Stack<Character> stack = new Stack<>();

        for (Character c : expresion.toCharArray()) 
        {
            switch (c) 
            {
                case '(':
                    stack.push(c);
                    break;
                case ')':
                    while (!stack.peek().equals('(')) 
                    {
                        postfix += stack.pop();
                    }
                    stack.pop();
                    break;
                default:
                    while (stack.size() > 0) 
                    {
                        Character peekedChar = stack.peek();
                        Integer peekedCharPrecedence = getPrecedencia(peekedChar);
                        Integer currentCharPrecedence = getPrecedencia(c);
                        if (peekedCharPrecedence >= currentCharPrecedence) 
                        {
                            postfix += stack.pop();

                        } 
                        else 
                        {
                            break;
                        }
                    }
                    stack.push(c);
                    break;
            }

        }
        while (stack.size() > 0)
        {
            postfix += stack.pop();

        }
        return postfix;
    }    
}
