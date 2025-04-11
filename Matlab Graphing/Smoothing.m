A=readmatrix('y1.csv')
An= 0.1*randn(1,101)+A(2,:);
Ar= 0.1*randn(1,101)+A(1,:);
Qn=smoothdata(An);
%reads in the csv as a matrix
B=readmatrix('y2.csv');
%salts the data from the 2nd row of the matrix which is the y values
Bn= 0.1*randn(1,101)+B(2,:);
Br= 0.1*randn(1,101)+B(1,:);
%smooths the data in y values
Vn=smoothdata(Bn);

C=readmatrix('y3.csv');
Cn=0.1*randn(1,101)+C(2,:);
Rn=smoothdata(Cn);

hold on
plot(A(1,:),Qn)
plot(B(1,:),Vn);
plot(C(1,:),Rn);
hold off