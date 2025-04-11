x=0:0.1:10;
y1=x.^(3)+3;
y2=x.^(4)+10;
y3=x.^(5)+1;
V= [x;y1];
Q= [x;y2];
R= [x;y3];
writematrix(V,'y1.csv');
writematrix(Q, 'y2.csv');
writematrix( R, 'y3.csv');

hold on
plot(x,y1);
plot(x,y2);
plot(x,y3);
hold off