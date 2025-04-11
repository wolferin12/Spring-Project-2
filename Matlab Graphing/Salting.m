A=readmatrix('y1.csv')
An= 0.1*randn(1,101)+A(2,:);
Ar= 0.1*randn(1,101)+A(1,:);

B=readmatrix('y2.csv');
Bn= 0.1*randn(1,101)+B(2,:);
Br= 0.1*randn(1,101)+B(1,:);


C=readmatrix('y3.csv');
Cn=0.1*randn(1,101)+C(2,:);
Cb=0.1*randn(1,101)+C(1,:);
R=[Cb,Cn];
%writematrix(R,'Salty3.csv');

hold on
plot(Ar,An);
plot(Br,Bn);
plot(Cb,Cn);
hold off