set terminal png enhanced size 1024,768 crop
set key below
set style data lines
set yrange [-0.1:*]
set xlabel 'Rang des signes (0=le plus fréquent)'
set ylabel "Logarithme (base 10) du nombre d'apparitions"
set output 'database-0.png'
plot "database-data.txt" using 1:2 with lines lw 2 title ""
