The deployment consists of two services with dockers, one for spring and one for react. Two load balancers were deployed to send the requests to the services. The dns names for the load balancers were used for routing the requests.

For simplicity, one VPC was used without any subnetting. Both services are publicly accessible.

The final url to see it in action: http://reactlb-2088109173.us-east-1.elb.amazonaws.com

