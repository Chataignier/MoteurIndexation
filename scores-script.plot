set terminal png enhanced size 1024,768 crop
set key below
set style data lines
set xlabel 'Rang du document'
set ylabel 'Score'
set yrange [-0.1:1.1]
set output 'scores-1.png'
plot "scores-data.txt" using 1:2 with lines lw 2 title ""
