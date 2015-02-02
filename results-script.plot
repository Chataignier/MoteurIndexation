set terminal png enhanced size 1024,768 crop
set key below
set style data lines
set xlabel 'Nombre de documents réponses'
set ylabel 'Proportion des documents du groupe'
set yrange [-0.1:1.1]
set output 'results-0.png'
plot "results-data.txt" using 1:2 with lines lw 2 title "tele", "results-data.txt" using 1:3 with lines lw 2 title "capitales", "results-data.txt" using 1:4 with lines lw 2 title "polytech", "results-data.txt" using 1:5 with lines lw 2 title "cinema", "results-data.txt" using 1:6 with lines lw 2 title "musique", "results-data.txt" using 1:7 with lines lw 2 title "loisirs", "results-data.txt" using 1:8 with lines lw 2 title "sport", "results-data.txt" using 1:9 with lines lw 2 title "presidentielles", "results-data.txt" using 1:10 with lines lw 2 title "medecine", "results-data.txt" using 1:11 with lines lw 2 title "animaux"