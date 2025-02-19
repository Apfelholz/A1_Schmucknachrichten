# **ILP-Modell zur Vermeidung von Präfixen**
**Gegeben:** Eine Menge von Integer-Variablen \( x_1, x_2, \dots, x_n \), die beliebige ganze Zahlen sein können.  
**Ziel:** Sicherstellen, dass keine Zahl \( x_i \) eine andere Zahl \( x_j \) als Präfix enthält.

---

## **1. Definition der Variablen**
Wir definieren die folgenden Variablen für unser ILP-Modell:

- **\( x_i \in \mathbb{Z}^+ \)**: Ganze Zahl für jedes \( i \).
- **\( L_i \)**: Anzahl der Dezimalziffern von \( x_i \).
- **\( d_{i,k} \in \{0,1,2,\dots,9\} \)**: Die k-te Dezimalziffer von \( x_i \), wobei \( k = 1,2,\dots,L_i \).
- **\( p_{ij} \in \{0,1\} \)**: Binärvariable, die angibt, ob \( x_i \) ein Präfix von \( x_j \) ist:
  
  \[
  p_{ij} =
  \begin{cases} 
  1, & \text{falls } x_i \text{ ein Präfix von } x_j \text{ ist} \\
  0, & \text{sonst}
  \end{cases}
  \]

---

## **2. Zerlegung jeder Zahl in ihre Ziffern**
Da ILP keine direkten String-Operationen unterstützt, müssen wir jede Zahl \( x_i \) in ihre Dezimalziffern zerlegen:

\[
x_i = \sum_{k=1}^{L_i} 10^{(L_i - k)} d_{i,k}
\]

**Beispiel:**  
Falls \( x_i = 1234 \), dann ist die Zerlegung:

\[
L_i = 4, \quad d_{i,1} = 1, \quad d_{i,2} = 2, \quad d_{i,3} = 3, \quad d_{i,4} = 4
\]

Die Formel stellt sicher, dass:

\[
1234 = (10^3 \cdot 1) + (10^2 \cdot 2) + (10^1 \cdot 3) + (10^0 \cdot 4)
\]

---

## **3. Präfix-Bedingung**
Eine Zahl \( x_i \) ist genau dann ein Präfix von \( x_j \), wenn:
1. Die ersten \( L_i \) Ziffern von \( x_j \) mit denen von \( x_i \) übereinstimmen:
   \[
   d_{i,k} = d_{j,k}, \quad \forall k = 1,2,...,L_i
   \]
2. Die Länge von \( x_j \) größer oder gleich der Länge von \( x_i \) ist:
   \[
   L_j \geq L_i
   \]

Um diese Bedingung in ILP umzusetzen, verwenden wir die **Big-M-Methode**:

\[
d_{i,k} - d_{j,k} \leq M (1 - p_{ij}), \quad \forall k = 1,2,...,L_i
\]

\[
d_{j,k} - d_{i,k} \leq M (1 - p_{ij}), \quad \forall k = 1,2,...,L_i
\]

wobei \( M \) eine große Zahl ist (z. B. 10).  
Diese Bedingungen sorgen dafür, dass:
- Falls \( p_{ij} = 1 \), dann gilt \( d_{i,k} = d_{j,k} \) für alle relevanten \( k \).
- Falls \( p_{ij} = 0 \), dann gibt es keine Einschränkung.

Zusätzlich verbieten wir, dass \( x_i \) ein Präfix von \( x_j \) sein kann:

\[
p_{ij} = 0, \quad \forall i \neq j
\]

---

## **4. Zusammenfassung der ILP-Constraints**
### **Hauptgleichungen:**
1. **Zahlenzerlegung**  
   \[
   x_i = \sum_{k=1}^{L_i} 10^{(L_i - k)} d_{i,k}
   \]
2. **Präfix-Bedingung mit Big-M-Methode**  
   \[
   d_{i,k} - d_{j,k} \leq M (1 - p_{ij}), \quad \forall k = 1,2,...,L_i
   \]
   \[
   d_{j,k} - d_{i,k} \leq M (1 - p_{ij}), \quad \forall k = 1,2,...,L_i
   \]
3. **Verbot von Präfixen**  
   \[
   p_{ij} = 0, \quad \forall i \neq j
   \]

---

## **Fazit**
Mit diesen Bedingungen wird garantiert, dass keine Zahl eine andere als Präfix enthält. Falls du eine Erweiterung benötigst (z. B. Vermeidung von Teilstrings statt nur Präfixen), kann das Modell entsprechend angepasst werden. 😊
